/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.astronomia.sistema;

import com.Excepcion;
import com.astronomia.DiaJuliano;
import static com.tipoCalculo.APROXIMADO;
import static com.tipoCalculo.PRECISO;
import com.unidades.CoordenadasGeograficas;
import com.unidades.Grados;
import static com.unidades.Herramienta.seno;
import static com.unidades.Herramienta.tangente;
import static com.unidades.Herramienta.coseno;
import com.unidades.Hora;
import com.unidades.Radianes;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Ecuatorial
{
   private Grados declinacion;
   private Hora ascensionRecta;
   private double distancia=0;
   
   @Override
    public String toString()
    {
        return "("+getDeclinacion().toString()+","+getAscensionRecta().toString()+")";
    }
   
   public Ecuatorial(Grados declinacion, Hora ascensionRecta)
   {
       this.declinacion=declinacion;
       this.ascensionRecta=ascensionRecta;
   }
   public Ecuatorial(Grados declinacion, Hora ascensionRecta, double distancia)
   {
       this.declinacion=declinacion;
       this.ascensionRecta=ascensionRecta;
       this.distancia=distancia;
   }
   
    public Ecuatorial(Ecuatorial coordenadas)
   {
       this.declinacion=coordenadas.declinacion;
       this.ascensionRecta=coordenadas.ascensionRecta;
       this.distancia=coordenadas.distancia;
   }
   
   public Ecliptica toEcliptica(Grados oblicuidadEcliptica)
   {
       Grados longitud=Grados.atan2(seno(getAscensionRecta())*coseno(oblicuidadEcliptica)+tangente(getDeclinacion())*seno(oblicuidadEcliptica),coseno(getAscensionRecta())).Ajuste();
       Grados latitud=Grados.asin(seno(getDeclinacion())*coseno(oblicuidadEcliptica)-coseno(getDeclinacion())*seno(oblicuidadEcliptica)*seno(getAscensionRecta())).Ajuste();
       return new Ecliptica(longitud, latitud);
   }
   
   public Horizontal toHorizontal(Grados latitud, Grados longitud, DiaJuliano diaJuliano) throws Excepcion
   {       
       Grados oblicuidadEcliptica =diaJuliano.getOblicuidadEcliptica(APROXIMADO);
       Hora anguloHorarioLocal=diaJuliano.getHoraSideralGreenwich(oblicuidadEcliptica).minus(Hora.valueOf(longitud)).minus(getAscensionRecta());
       Grados acimut=Grados.atan2(seno(anguloHorarioLocal),coseno(anguloHorarioLocal)*seno(latitud)-tangente(getDeclinacion())*coseno(latitud)).plus(new Grados(180)).Ajuste();
       Grados elevacion=Grados.asin(seno(latitud)*seno(getDeclinacion())+coseno(latitud)*coseno(getDeclinacion())*coseno(anguloHorarioLocal)).Ajuste();
       return new Horizontal(acimut, elevacion);
   }
   
   static public Grados getSeparacionAngular(Ecuatorial ce1,Ecuatorial ce2)
   {
      double c=seno(ce1.getDeclinacion())*seno(ce2.getDeclinacion())+coseno(ce1.getDeclinacion())*coseno(ce2.getDeclinacion())*coseno(ce1.getAscensionRecta().minus(ce2.getAscensionRecta()));
      if(c<0.999995)
      {
       return Grados.acos(c);
      }
      else
      {
          double c1=Grados.valueOf(ce1.getAscensionRecta().minus(ce2.getAscensionRecta())).getValor();
          double c2=ce1.getDeclinacion().minus(ce2.getDeclinacion()).getValor();
          double c3=(ce1.getDeclinacion().getValor()+ce2.getDeclinacion().getValor())/2;
          return new Grados(sqrt(pow(c1*coseno(new Grados(c3)),2)+pow(c2,2)));
      }    
      
   }  
   
    public Ecuatorial CorreccionAberracion(DiaJuliano dia) throws Excepcion
    {
        double T=dia.getSiglosDesde2000();
        
        double L2=3.1761467+1021.3285546*T; //longitud media de Venus referido al equinoccio medio de J2000 en radianes
        double L3=1.7534703+628.3075849*T; //longitud Marte de venus referido al equinoccio medio de J2000 en radianes
        double L4=6.2034809+334.0612431*T;
        double L5=0.5995465+52.9690965*T;
        double L6=0.8740168+21.3299095*T;
        double L7=5.4812939+7.4781599*T;
        double L8=5.3118863+3.8133036*T; //longitud media de Neptuno referido al equinoccio medio de J2000 en radianes
        double l=3.8103444+8399.6847337*T;//longitud media de la Luna referido al equinoccio medio de J2000 en radianes
        double D=5.19844667+7771.3771486*T;
        double m=2.3555559+8328.6914289*T;
        double F=1.6279052+8433.4661601*T;
        
        double xs=0;
        xs += (-1719914-2*T)*sin(L3);
        xs += (6434+141*T)*sin(2*L3);
        xs += (715)*sin(L5);
        xs += (715)*sin(l);
        xs += (486-5*T)*sin(3*L3);
        xs += (159)*sin(L6);        
        xs += (0)*sin(F);
        xs += (39)*sin(l+m);
        xs += (33)*sin(2*L5);
        xs += (31)*sin(2*L3-L5);
        xs += (8)*sin(3*L3-8*L4+3*L5);
        xs += (8)*sin(5*L3-8*L4+3*L5);
        xs += (21)*sin(2*L2-L3);
        xs += (-19)*sin(L2);
        xs += (17)*sin(L7);
        xs += (16)*sin(L3-2*L5);
        xs += (16)*sin(L8);
        xs += (11)*sin(L3+L5);
        xs += (0)*sin(2*L2-2*L3);
        xs += (-11)*sin(L3-L5);
        xs += (-7)*sin(4*L3);
        xs += (-10)*sin(3*L3-2*L5);
        xs += (-9)*sin(L2-2*L3);
        xs += (-9)*sin(2*L2-3*L3);
        xs += (0)*sin(2*L6);
        xs += (0)*sin(2*L2-4*L3);
        xs += (8)*sin(3*L3-2*L4);
        xs += (8)*sin(l+2*D-m);
        xs += (-4)*sin(8*L2-12*L3);
        xs += (-4)*sin(8*L2-14*L3);
        xs += (-6)*sin(2*L4);
        xs += (-1)*sin(3*L2-4*L3);
        xs += (4)*sin(2*L3-2*L5);
        xs += (0)*sin(3*L2-3*L3);
        xs += (5)*sin(2*L3-2*L4);
        xs += (5)*sin(l-2*D);
        
        double xc=0;
        xc += (-25)*cos(L3);
        xc += (28007-107*T)*cos(2*L3);
        xc += (0)*cos(L5);
        xc += (0)*cos(l);
        xc += (-236-4*T)*cos(3*L3);
        xc += (0)*cos(L6);       
        xc += (0)*cos(F);
        xc += (0)*cos(l+m);
        xc += (-10)*cos(2*L5);
        xc += (1)*cos(2*L3-L5);
        xc += (-28)*cos(3*L3-8*L4+3*L5);
        xc += (-28)*cos(5*L3-8*L4+3*L5);
        xc += (0)*cos(2*L2-L3);
        xc += (0)*cos(L2);
        xc += (0)*cos(L7);
        xc += (0)*cos(L3-2*L5);
        xc += (0)*cos(L8);
        xc += (-1)*cos(L3+L5);
        xc += (-11)*cos(2*L2-2*L3);
        xc += (-2)*cos(L3-L5);
        xc += (-8)*cos(4*L3);
        xc += (0)*cos(3*L3-2*L5);
        xc += (0)*cos(L2-2*L3);
        xc += (0)*cos(2*L2-3*L3);
        xc += (-9)*cos(2*L6);
        xc += (-9)*cos(2*L2-4*L3);
        xc += (0)*cos(3*L3-2*L4);
        xc += (0)*cos(l+2*D-m);
        xc += (-7)*cos(8*L2-12*L3);
        xc += (-7)*cos(8*L2-14*L3);
        xc += (-5)*cos(2*L4);
        xc += (-1)*cos(3*L2-4*L3);
        xc += (-6)*cos(2*L3-2*L5);
        xc += (-7)*cos(3*L2-3*L3);
        xc += (-5)*cos(2*L3-2*L4);
        xc += (0)*cos(l-2*D);
        
        double ys=0;
        ys += (25-13*T)*sin(L3);
        ys += (25697-95*T)*sin(2*L3);
        ys += (6)*sin(L5);
        ys += (0)*sin(l);
        ys += (-216-4*T)*sin(3*L3);
        ys += (2)*sin(L6);       
        ys += (0)*sin(F);
        ys += (0)*sin(l+m);
        ys += (-9)*sin(2*L5);
        ys += (1)*sin(2*L3-L5);
        ys += (25)*sin(3*L3-8*L4+3*L5);
        ys += (-25)*sin(5*L3-8*L4+3*L5);
        ys += (0)*sin(2*L2-L3);
        ys += (0)*sin(L2);
        ys += (0)*sin(L7);
        ys += (0)*sin(L3-2*L5);
        ys += (1)*sin(L8);
        ys += (-1)*sin(L3+L5);
        ys += (-10)*sin(2*L2-2*L3);
        ys += (-2)*sin(L3-L5);
        ys += (-8)*sin(4*L3);
        ys += (0)*sin(3*L3-2*L5);
        ys += (0)*sin(L2-2*L3);
        ys += (0)*sin(2*L2-3*L3);
        ys += (-8)*sin(2*L6);
        ys += (8)*sin(2*L2-4*L3);
        ys += (0)*sin(3*L3-2*L4);
        ys += (0)*sin(l+2*D-m);
        ys += (-6)*sin(8*L2-12*L3);
        ys += (6)*sin(8*L2-14*L3);
        ys += (-4)*sin(2*L4);
        ys += (-2)*sin(3*L2-4*L3);
        ys += (-5)*sin(2*L3-2*L5);
        ys += (-6)*sin(3*L2-3*L3);
        ys += (-4)*sin(2*L3-2*L4);
        ys += (0)*sin(l-2*D);
        
        double yc=0;
        yc += (1578089+156*T)*cos(L3);
        yc += (-5904-130*T)*cos(2*L3);
        yc += (-657)*cos(L5);
        yc += (-656)*cos(l);
        yc += (-446+5*T)*cos(3*L3);
        yc += (-147)*cos(L6);       
        yc += (26)*cos(F);
        yc += (-36)*cos(l+m);
        yc += (-30)*cos(2*L5);
        yc += (-28)*cos(2*L3-L5);
        yc += (8)*cos(3*L3-8*L4+3*L5);
        yc += (-8)*cos(5*L3-8*L4+3*L5);
        yc += (-19)*cos(2*L2-L3);
        yc += (17)*cos(L2);
        yc += (-16)*cos(L7);
        yc += (15)*cos(L3-2*L5);
        yc += (-15)*cos(L8);
        yc += (-10)*cos(L3+L5);
        yc += (0)*cos(2*L2-2*L3);
        yc += (9)*cos(L3-L5);
        yc += (6)*cos(4*L3);
        yc += (9)*cos(3*L3-2*L5);
        yc += (-9)*cos(L2-2*L3);
        yc += (-8)*cos(2*L2-3*L3);
        yc += (0)*cos(2*L6);
        yc += (0)*cos(2*L2-4*L3);
        yc += (-8)*cos(3*L3-2*L4);
        yc += (-7)*cos(l+2*D-m);
        yc += (4)*cos(8*L2-12*L3);
        yc += (-4)*cos(8*L2-14*L3);
        yc += (5)*cos(2*L4);
        yc += (-7)*cos(3*L2-4*L3);
        yc += (-4)*cos(2*L3-2*L5);
        yc += (0)*cos(3*L2-3*L3);
        yc += (-5)*cos(2*L3-2*L4);
        yc += (-5)*cos(l-2*D);
        
        double zs=0;
        zs += (10+32*T)*sin(L3);
        zs += (11141-48*T)*sin(2*L3);
        zs += (-15)*sin(L5);
        zs += (0)*sin(l);
        zs += (-94)*sin(3*L3);
        zs += (-6)*sin(L6);       
        zs += (0)*sin(F);
        zs += (0)*sin(l+m);
        zs += (-5)*sin(2*L5);
        zs += (0)*sin(2*L3-L5);
        zs += (11)*sin(3*L3-8*L4+3*L5);
        zs += (-11)*sin(5*L3-8*L4+3*L5);
        zs += (0)*sin(2*L2-L3);
        zs += (0)*sin(L2);
        zs += (0)*sin(L7);
        zs += (1)*sin(L3-2*L5);
        zs += (-3)*sin(L8);
        zs += (-1)*sin(L3+L5);
        zs += (-4)*sin(2*L2-2*L3);
        zs += (-1)*sin(L3-L5);
        zs += (-3)*sin(4*L3);
        zs += (0)*sin(3*L3-2*L5);
        zs += (0)*sin(L2-2*L3);
        zs += (0)*sin(2*L2-3*L3);
        zs += (-3)*sin(2*L6);
        zs += (3)*sin(2*L2-4*L3);
        zs += (0)*sin(3*L3-2*L4);
        zs += (0)*sin(l+2*D-m);
        zs += (-3)*sin(8*L2-12*L3);
        zs += (3)*sin(8*L2-14*L3);
        zs += (-2)*sin(2*L4);
        zs += (1)*sin(3*L2-4*L3);
        zs += (-2)*sin(2*L3-2*L5);
        zs += (-3)*sin(3*L2-3*L3);
        zs += (-2)*sin(2*L3-2*L4);
        zs += (0)*sin(l-2*D);
        
        double zc=0;
        zc += (684185-358*T)*cos(L3);
        zc += (-2559-55*T)*cos(2*L3);
        zc += (-282)*cos(L5);
        zc += (-285)*cos(l);
        zc += (-193)*cos(3*L3);
        zc += (-61)*cos(L6);       
        zc += (-59)*cos(F);
        zc += (-16)*cos(l+m);
        zc += (-13)*cos(2*L5);
        zc += (-12)*cos(2*L3-L5);
        zc += (3)*cos(3*L3-8*L4+3*L5);
        zc += (-3)*cos(5*L3-8*L4+3*L5);
        zc += (-8)*cos(2*L2-L3);
        zc += (8)*cos(L2);
        zc += (-7)*cos(L7);
        zc += (7)*cos(L3-2*L5);
        zc += (-6)*cos(L8);
        zc += (-5)*cos(L3+L5);
        zc += (0)*cos(2*L2-2*L3);
        zc += (4)*cos(L3-L5);
        zc += (3)*cos(4*L3);
        zc += (4)*cos(3*L3-2*L5);
        zc += (-4)*cos(L2-2*L3);
        zc += (-4)*cos(2*L2-3*L3);
        zc += (0)*cos(2*L6);
        zc += (0)*cos(2*L2-4*L3);
        zc += (-3)*cos(3*L3-2*L4);
        zc += (-3)*cos(l+2*D-m);
        zc += (2)*cos(8*L2-12*L3);
        zc += (-2)*cos(8*L2-14*L3);
        zc += (2)*cos(2*L4);
        zc += (-4)*cos(3*L2-4*L3);
        zc += (-2)*cos(2*L3-2*L5);
        zc += (0)*cos(3*L2-3*L3);
        zc += (-2)*cos(2*L3-2*L4);
        zc += (-2)*cos(l-2*D);
        
        double X=xs+xc;
        double Y=ys+yc;
        double Z=zs+zc;
        double c=17314463350.0; //velocidad de la luz
        
        double ar=(Y*coseno(getAscensionRecta())-X*seno(getAscensionRecta()))/(c*coseno(getDeclinacion())); //en radianes
        double d=-((X*coseno(ascensionRecta)+Y*seno(ascensionRecta))*seno(declinacion)-Z*coseno(declinacion))/c;
        return new Ecuatorial(getDeclinacion().plus(Grados.valueOf(new Radianes(d))), getAscensionRecta().plus(Hora.valueOf(new Radianes(ar))));
        
    }   
    
    
    public Ecuatorial Geocentrico2Topocentrico(CoordenadasGeograficas punto, DiaJuliano dia) throws Excepcion
    {
        if(getDistancia()==0) return new Ecuatorial(getDeclinacion(), getAscensionRecta());
        else
        {
           Grados paralaje = Grados.asin(seno(new Grados(8.794/3600))/getDistancia());
           double a=6378140;
           double f=1/298.257;
           double b=a*(1-f);
           
           Grados u =Grados.atan2(b*tangente(punto.getLatitud()), a);
           
           double A=b*seno(u)/a+punto.getAltitud()*seno(punto.getLatitud())/6378140;
           double B=coseno(u)+punto.getAltitud()*coseno(punto.getLatitud())/6378140;
           Grados oblicuidadEcliptica =dia.getOblicuidadEcliptica(PRECISO);
           Hora anguloHorarioLocal=dia.getHoraSideralGreenwich(oblicuidadEcliptica).minus(Hora.valueOf(punto.getLongitud())).minus(getAscensionRecta());
           
           
           Hora incrAR=Hora.atan2(-B*seno(paralaje)*seno(anguloHorarioLocal),coseno(getDeclinacion())-B*seno(paralaje)*coseno(anguloHorarioLocal));
           Grados nuevaDE=Grados.atan2((seno(getDeclinacion())-A*seno(paralaje))*coseno(incrAR), coseno(getDeclinacion())-B*seno(paralaje)*coseno(anguloHorarioLocal));
           
           return new Ecuatorial(nuevaDE,getAscensionRecta().plus(incrAR), getDistancia());
        }    
    }        

    /**
     * @return the declinacion
     */
    public Grados getDeclinacion()
    {
        return declinacion;
    }

    /**
     * @param declinacion the declinacion to set
     */
    public void setDeclinacion(Grados declinacion)
    {
        this.declinacion = declinacion;
    }

    /**
     * @return the ascensionRecta
     */
    public Hora getAscensionRecta()
    {
        return ascensionRecta;
    }

    /**
     * @param ascensionRecta the ascensionRecta to set
     */
    public void setAscensionRecta(Hora ascensionRecta)
    {
        this.ascensionRecta = ascensionRecta;
    }

    /**
     * @return the distancia
     */
    public double getDistancia()
    {
        return distancia;
    }

    /**
     * @param distancia the distancia to set
     */
    public void setDistancia(double distancia)
    {
        this.distancia = distancia;
    }
}

