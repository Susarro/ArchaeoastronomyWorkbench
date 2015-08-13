/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia;

import com.Excepcion;
import com.astronomia.orbital.ElementoOrbital;
import com.astronomia.sistema.Ecliptica;
import com.astronomia.sistema.Ecuatorial;
import com.astronomia.sistema.Heliocentrico;
import com.enumPlaneta;
import static com.enumPlaneta.JUPITER;
import static com.enumPlaneta.LUNA;
import static com.enumPlaneta.MARTE;
import static com.enumPlaneta.MERCURIO;
import static com.enumPlaneta.SATURNO;
import static com.enumPlaneta.SOL;
import static com.enumPlaneta.TIERRA;
import static com.enumPlaneta.VENUS;
import com.tipoCalculo;
import com.unidades.Grados;
import static com.unidades.Herramienta.coseno;
import static com.unidades.Herramienta.seno;
import static com.unidades.Herramienta.tangente;
import static java.lang.Math.abs;
import static java.lang.Math.log10;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Planeta
{
    enumPlaneta planeta;
    
    public String toString()
    {
        return planeta.toString();
    }        
    
    public Planeta(enumPlaneta planeta)
    {
        this.planeta=planeta;                
    }        
    
    public static double getDistancia(enumPlaneta planeta, DiaJuliano epoch) throws Excepcion
    {
        if (planeta == MERCURIO || planeta == VENUS || planeta == MARTE || planeta == JUPITER || planeta == SATURNO)
        {
            DiaJuliano dia = new DiaJuliano(epoch);
            double error = 1e-1;

            Grados L = null;
            Grados B = null;
            double R = 0;
            double x = 0;
            double y = 0;
            double z = 0;

            Grados L0 = Heliocentrico.getLongitud(TIERRA, dia);
            Grados B0 = Heliocentrico.getLatitud(TIERRA, dia);
            double R0 = Heliocentrico.getRadio(TIERRA, dia);
            double distancia=0;

            for (int i = 0; i < 1000000; i++)
            {
                Grados L_ = Heliocentrico.getLongitud(planeta, dia);
                Grados B_ = Heliocentrico.getLatitud(planeta, dia);
                double R_ = Heliocentrico.getRadio(planeta, dia);

                x = R_ * coseno(B_) * coseno(L_) - R0 * coseno(B0) * coseno(L0);
                y = R_ * coseno(B_) * seno(L_) - R0 * coseno(B0) * seno(L0);
                z = R_ * seno(B_) - R0 * seno(B0);

                distancia = sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2));
                double tiempoLuz = 0.0057755183 * distancia;

                if (L != null && B != null)
                {
                    double e = sqrt(pow(L.getValor() - L_.getValor(), 2) + pow(B.getValor() - B_.getValor(), 2) + pow(R - R_, 2));
                    if (e <= error)
                    {
                        break;
                    }
                }
                L = L_;
                B = B_;
                R = R_;
                dia = new DiaJuliano(epoch.diaJuliano - tiempoLuz);
            }
            
            return distancia;
        }
        else if(planeta==SOL)
        {
           return Heliocentrico.getRadio(TIERRA, epoch);
        }
        else if(planeta==LUNA)
        {
           InfoLuna il=Luna.getInfoLuna(epoch);
           return il.coordenadas.getDistancia();
        }                
        else
        {
            return 0;
        }    
    }        
    
    public Ecuatorial getPosicionAparente(DiaJuliano epoch) throws Excepcion
    {
        if (planeta == MERCURIO || planeta == VENUS || planeta == MARTE || planeta == JUPITER || planeta == SATURNO)
        {
            DiaJuliano dia = new DiaJuliano(epoch);
            double error = 1e-1;

            Grados L = null;
            Grados B = null;
            double R = 0;
            double x = 0;
            double y = 0;
            double z = 0;

            Grados L0 = Heliocentrico.getLongitud(TIERRA, dia);
            Grados B0 = Heliocentrico.getLatitud(TIERRA, dia);
            double R0 = Heliocentrico.getRadio(TIERRA, dia);
            double distancia=0;

            for (int i = 0; i < 1000000; i++)
            {
                Grados L_ = Heliocentrico.getLongitud(planeta, dia);
                Grados B_ = Heliocentrico.getLatitud(planeta, dia);
                double R_ = Heliocentrico.getRadio(planeta, dia);

                x = R_ * coseno(B_) * coseno(L_) - R0 * coseno(B0) * coseno(L0);
                y = R_ * coseno(B_) * seno(L_) - R0 * coseno(B0) * seno(L0);
                z = R_ * seno(B_) - R0 * seno(B0);

                distancia = sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2));
                double tiempoLuz = 0.0057755183 * distancia;

                if (L != null && B != null)
                {
                    double e = sqrt(pow(L.getValor() - L_.getValor(), 2) + pow(B.getValor() - B_.getValor(), 2) + pow(R - R_, 2));
                    if (e <= error)
                    {
                        break;
                    }
                }
                L = L_;
                B = B_;
                R = R_;
                dia = new DiaJuliano(epoch.diaJuliano - tiempoLuz);
            }

            Grados longitudGeocentrica = Grados.atan2(y, x);
            Grados latitudGeocentrica = Grados.atan2(z, sqrt(pow(x, 2) + pow(y, 2)));

            double T = dia.getSiglosDesde2000();

            ElementoOrbital eo = ElementoOrbital.get(TIERRA, dia);
            double ex = eo.excentricidad;
            Grados pi = eo.longitudPerihelion;

            Grados longitudSol = L0.plus(new Grados(180));            

            double K = 20.49552;
            double incrLongitud = (-K * coseno(longitudSol.minus(longitudGeocentrica)) + ex * K * coseno(pi.minus(longitudGeocentrica))) / coseno(latitudGeocentrica); //segundos
            double incrLatitud = -K * seno(latitudGeocentrica) * seno(longitudSol.minus(longitudGeocentrica)) - ex * seno(pi.minus(longitudGeocentrica));

            Grados L2 = new Grados(longitudGeocentrica.getValor() - 1.397 * T - 0.00031 * pow(T, 2));
            double incrLongitud2 = -0.09033 + 0.03916 * (coseno(L2) + seno(L2)) * tangente(latitudGeocentrica);//segundos
            double incrLatitud2 = 0.03916 * (coseno(L2) - seno(L2));

            longitudGeocentrica = longitudGeocentrica.plus(new Grados(incrLongitud / 3600));
            latitudGeocentrica = latitudGeocentrica.plus(new Grados(incrLatitud / 3600));

            Grados nutacion = dia.getNutacionLongitud();

            longitudGeocentrica = longitudGeocentrica.plus(new Grados(incrLongitud2 / 3600));
            latitudGeocentrica = latitudGeocentrica.plus(new Grados(incrLatitud2 / 3600));

            longitudGeocentrica = longitudGeocentrica.plus(nutacion);

            return new Ecliptica(longitudGeocentrica, latitudGeocentrica).toEcuatorial(dia.getOblicuidadEcliptica(tipoCalculo.PRECISO),distancia);

        }
        else
        {
            throw new Excepcion("Sólo Mercurio, Venus, Marte, Júpiter o Saturno");
        }
    }
    
     public InfoPlaneta getInfoPlaneta(DiaJuliano epoch) throws Excepcion
    {
        if (planeta == MERCURIO || planeta == VENUS || planeta == MARTE || planeta == JUPITER || planeta == SATURNO)
        {
            DiaJuliano dia = new DiaJuliano(epoch);
            double error = 1e-5;

            Grados L = null;
            Grados B = null;
            double R = 0;
            double x = 0;
            double y = 0;
            double z = 0;

            Grados L0 = Heliocentrico.getLongitud(TIERRA, dia);
            Grados B0 = Heliocentrico.getLatitud(TIERRA, dia);
            double R0 = Heliocentrico.getRadio(TIERRA, dia);
            double distancia=0;

            for (int i = 0; i < 1000000; i++)
            {
                Grados L_ = Heliocentrico.getLongitud(planeta, dia);
                Grados B_ = Heliocentrico.getLatitud(planeta, dia);
                double R_ = Heliocentrico.getRadio(planeta, dia);

                x = R_ * coseno(B_) * coseno(L_) - R0 * coseno(B0) * coseno(L0);
                y = R_ * coseno(B_) * seno(L_) - R0 * coseno(B0) * seno(L0);
                z = R_ * seno(B_) - R0 * seno(B0);

                distancia = sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2));
                double tiempoLuz = 0.0057755183 * distancia;

                if (L != null && B != null)
                {
                    double e = sqrt(pow(L.getValor() - L_.getValor(), 2) + pow(B.getValor() - B_.getValor(), 2) + pow(R - R_, 2));
                    if (e <= error)
                    {
                        break;
                    }
                }
                L = L_;
                B = B_;
                R = R_;
                dia = new DiaJuliano(epoch.diaJuliano - tiempoLuz);
            }

            Grados longitudGeocentrica = Grados.atan2(y, x);
            Grados latitudGeocentrica = Grados.atan2(z, sqrt(pow(x, 2) + pow(y, 2)));

            double T = dia.getSiglosDesde2000();

            ElementoOrbital eo = ElementoOrbital.get(TIERRA, dia);
            double ex = eo.excentricidad;
            Grados pi = eo.longitudPerihelion;

            Grados longitudSol = L0.plus(new Grados(180));            

            double K = 20.49552;
            double incrLongitud = (-K * coseno(longitudSol.minus(longitudGeocentrica)) + ex * K * coseno(pi.minus(longitudGeocentrica))) / coseno(latitudGeocentrica); //segundos
            double incrLatitud = -K * seno(latitudGeocentrica) * seno(longitudSol.minus(longitudGeocentrica)) - ex * seno(pi.minus(longitudGeocentrica));

            Grados L2 = new Grados(longitudGeocentrica.getValor() - 1.397 * T - 0.00031 * pow(T, 2));
            double incrLongitud2 = -0.09033 + 0.03916 * (coseno(L2) + seno(L2)) * tangente(latitudGeocentrica);//segundos
            double incrLatitud2 = 0.03916 * (coseno(L2) - seno(L2));

            longitudGeocentrica = longitudGeocentrica.plus(new Grados(incrLongitud / 3600));
            latitudGeocentrica = latitudGeocentrica.plus(new Grados(incrLatitud / 3600));

            Grados nutacion = dia.getNutacionLongitud();

            longitudGeocentrica = longitudGeocentrica.plus(new Grados(incrLongitud2 / 3600));
            latitudGeocentrica = latitudGeocentrica.plus(new Grados(incrLatitud2 / 3600));

            longitudGeocentrica = longitudGeocentrica.plus(nutacion);
            double magnitudVisual=0;
            
            
            Grados anguloFase = Grados.acos((R-R0*coseno(B)*coseno(L.minus(L0)))/distancia);
            double fraccionIluminada = (1+coseno(anguloFase))/2;
            
            switch(planeta)
            {
                case MERCURIO:
                    magnitudVisual=-0.42+5*log10(R*distancia)+0.0380*anguloFase.getValor()-0.000273*pow(anguloFase.getValor(),2)+0.000002*pow(anguloFase.getValor(),3);
                    magnitudVisual=((int)(magnitudVisual*10))/10.0;
                    break;
                    case VENUS:
                    magnitudVisual=-4.40+5*log10(R*distancia)+0.0009*anguloFase.getValor()+0.000239*pow(anguloFase.getValor(),2)-0.00000065*pow(anguloFase.getValor(),3);                   
                    magnitudVisual=((int)(magnitudVisual*10))/10.0;
                    break;
                    case MARTE:
                    magnitudVisual=-1.52+5*log10(R*distancia)+0.016*anguloFase.getValor();
                   magnitudVisual=((int)(magnitudVisual*10))/10.0;
                    break;    
                    case JUPITER:
                    magnitudVisual=-9.40+5*log10(R*distancia)+0.005*anguloFase.getValor();
                    magnitudVisual=((int)(magnitudVisual*10))/10.0;
                    break;
                    case SATURNO:
                    Grados inclinacionPlanoAnillo=new Grados(28.075216-0.012998*T+0.000004*pow(T,2));    
                    Grados longitudNodoAscendente=new Grados(169.508470+1.394681*T+0.000412*pow(T,2));
                    Grados latitudTierraASaturno = Grados.asin(seno(inclinacionPlanoAnillo)*coseno(latitudGeocentrica)*seno(longitudGeocentrica.minus(longitudNodoAscendente))-coseno(inclinacionPlanoAnillo)*seno(latitudGeocentrica));
                    Grados longitudNodoAscendenteOrbitaSAturno=new Grados(113.6655+0.8771*T);
                    Grados L_=L.minus(new Grados(0.01759/R));
                    Grados B_=B.minus(new Grados(0.000764*coseno(L.minus(longitudNodoAscendenteOrbitaSAturno))/R));
                    Grados latitudSolASaturno=Grados.asin(seno(inclinacionPlanoAnillo)*coseno(B_)*seno(L_.minus(longitudNodoAscendente))-coseno(inclinacionPlanoAnillo)*seno(B_));
                    Grados U1= Grados.atan2(seno(inclinacionPlanoAnillo)*seno(B_)+coseno(inclinacionPlanoAnillo)*coseno(B_)*seno(L_.minus(longitudNodoAscendente)), coseno(B_)*coseno(L_.minus(longitudNodoAscendente)));
                    Grados U2= Grados.atan2(seno(inclinacionPlanoAnillo)*seno(latitudGeocentrica)+coseno(inclinacionPlanoAnillo)*coseno(latitudGeocentrica)*seno(longitudGeocentrica.minus(longitudNodoAscendente)), coseno(latitudGeocentrica)*coseno(longitudGeocentrica.minus(longitudNodoAscendente)));
                    double AU=U1.getValor()-U2.getValor();
                    if(AU>180) AU=360-AU;                        
                    magnitudVisual=-8.88+5*log10(R*distancia)+0.044*AU-2.60*abs(seno(latitudTierraASaturno))+1.25*pow(seno(latitudTierraASaturno),2);
                    magnitudVisual=((int)(magnitudVisual*10))/10.0;
                    break;    
            }    

            return new InfoPlaneta(new Ecliptica(longitudGeocentrica, latitudGeocentrica).toEcuatorial(dia.getOblicuidadEcliptica(tipoCalculo.PRECISO),distancia),magnitudVisual);

        }
        else
        {
            throw new Excepcion("Sólo Mercurio, Venus, Marte, Júpiter o Saturno");
        }
    }
    
}
