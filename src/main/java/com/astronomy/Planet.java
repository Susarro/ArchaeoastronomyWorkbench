/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy;

import com.ProcessException;
import com.astronomy.coordinate_system.Ecliptic;
import com.astronomy.coordinate_system.Equatorial;
import com.astronomy.coordinate_system.Heliocientric;
import com.PlanetEnum;
import static com.PlanetEnum.JUPITER;
import static com.PlanetEnum.MARS;
import static com.PlanetEnum.MERCURY;
import static com.PlanetEnum.SATURN;
import static com.PlanetEnum.SUN;
import static com.PlanetEnum.EARTH;
import static com.PlanetEnum.MOON;
import static com.PlanetEnum.VENUS;
import com.CalculusType;
import com.units.SexagesimalDegree;
import static java.lang.Math.log10;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static com.units.Tools.cosine;
import static com.units.Tools.sine;
import static com.units.Tools.tangent;
import static java.lang.Math.abs;

/**
 * Planets
 * 
 * @author MIGUEL_ANGEL
 */
public class Planet
{
    /**
     * Planet ID
     */
    PlanetEnum planeta;
    
    /**
     * 
     * @return Planet name
     */
    @Override
    public String toString()
    {
        return planeta.toString();
    }        
    
    /**
     * Constructor
     * @param planet Planet ID
     */
    public Planet(PlanetEnum planet)
    {
        this.planeta=planet;                
    }        
    
    /**
     * 
     * @param planet Planet ID, includes MERCURY, VENUS, MARS, JUPITER, SATURN, SUN and MOON
     * @param epoch Julian day
     * @return True distance to Earth
     * @throws ProcessException Format error
     */
    public static double getTrueDistanceToEarth(PlanetEnum planet, JulianDay epoch) throws ProcessException
    {
        if (planet == MERCURY || planet == VENUS || planet == MARS || planet == JUPITER || planet == SATURN)
        {
            JulianDay dia = new JulianDay(epoch);
            double error = 1e-1;

            SexagesimalDegree L = null;
            SexagesimalDegree B = null;
            double R = 0;
            double x;
            double y;
            double z;

            SexagesimalDegree L0 = Heliocientric.getLongitude(EARTH, dia);
            SexagesimalDegree B0 = Heliocientric.getLatitude(EARTH, dia);
            double R0 = Heliocientric.getRadius(EARTH, dia);
            double distancia=0;

            for (int i = 0; i < 1000000; i++)
            {
                SexagesimalDegree L_ = Heliocientric.getLongitude(planet, dia);
                SexagesimalDegree B_ = Heliocientric.getLatitude(planet, dia);
                double R_ = Heliocientric.getRadius(planet, dia);

                x = R_ * cosine(B_) * cosine(L_) - R0 * cosine(B0) * cosine(L0);
                y = R_ * cosine(B_) * sine(L_) - R0 * cosine(B0) * sine(L0);
                z = R_ * sine(B_) - R0 * sine(B0);

                distancia = sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2));
                double tiempoLuz = 0.0057755183 * distancia;

                if (L != null && B != null)
                {
                    double e = sqrt(pow(L.getValue() - L_.getValue(), 2) + pow(B.getValue() - B_.getValue(), 2) + pow(R - R_, 2));
                    if (e <= error)
                    {
                        break;
                    }
                }
                L = L_;
                B = B_;
                R = R_;
                dia = new JulianDay(epoch.julianDate - tiempoLuz);
            }
            
            return distancia;
        }
        else if(planet==SUN)
        {
           return Heliocientric.getRadius(EARTH, epoch);
        }
        else if(planet==MOON)
        {
           InfoMoon il=Moon.getInfoMoon(epoch);
           return il.position.getDistance();
        }                
        else
        {
            return 0;
        }    
    }        
    
    /**
     * 
     * @param epoch Julian day
     * @return Planet's apparent position in the Ecuatorial Coordinate System. 
     * Includes MERCURY, VENUS, MARS, JUPITER or SATURN
     * @throws ProcessException Only Mercury, Venus, Mars, Jupiter or Saturn 
     */
    public Equatorial getApparentPosition(JulianDay epoch) throws ProcessException
    {
        if (planeta == MERCURY || planeta == VENUS || planeta == MARS || planeta == JUPITER || planeta == SATURN)
        {
            JulianDay day = new JulianDay(epoch);
            double error = 1e-1;

            SexagesimalDegree L = null;
            SexagesimalDegree B = null;
            double R = 0;
            double x = 0;
            double y = 0;
            double z = 0;

            SexagesimalDegree L0 = Heliocientric.getLongitude(EARTH, day);
            SexagesimalDegree B0 = Heliocientric.getLatitude(EARTH, day);
            double R0 = Heliocientric.getRadius(EARTH, day);
            double distance=0;

            for (int i = 0; i < 1000000; i++)
            {
                SexagesimalDegree L_ = Heliocientric.getLongitude(planeta, day);
                SexagesimalDegree B_ = Heliocientric.getLatitude(planeta, day);
                double R_ = Heliocientric.getRadius(planeta, day);

                x = R_ * cosine(B_) * cosine(L_) - R0 * cosine(B0) * cosine(L0);
                y = R_ * cosine(B_) * sine(L_) - R0 * cosine(B0) * sine(L0);
                z = R_ * sine(B_) - R0 * sine(B0);

                distance = sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2));
                double lightTime = 0.0057755183 * distance;

                if (L != null && B != null)
                {
                    double e = sqrt(pow(L.getValue() - L_.getValue(), 2) + pow(B.getValue() - B_.getValue(), 2) + pow(R - R_, 2));
                    if (e <= error)
                    {
                        break;
                    }
                }
                L = L_;
                B = B_;
                R = R_;
                day = new JulianDay(epoch.julianDate - lightTime);
            }

            SexagesimalDegree GeocentricLongitude = SexagesimalDegree.atan2(y, x);
            SexagesimalDegree GeocentricLatitude = SexagesimalDegree.atan2(z, sqrt(pow(x, 2) + pow(y, 2)));

            double T = day.getCenturiesSince2000();

            PlanetaryOrbitElements eo = PlanetaryOrbitElements.get(EARTH, day);
            double ex = eo.eccentricity;
            SexagesimalDegree pi = eo.perihelionLongitude;

            SexagesimalDegree SunLongitude = L0.plus(new SexagesimalDegree(180));            

            double K = 20.49552;
            double incrLongitud = (-K * cosine(SunLongitude.minus(GeocentricLongitude)) + ex * K * cosine(pi.minus(GeocentricLongitude))) / cosine(GeocentricLatitude); //segundos
            double incrLatitud = -K * sine(GeocentricLatitude) * sine(SunLongitude.minus(GeocentricLongitude)) - ex * sine(pi.minus(GeocentricLongitude));

            SexagesimalDegree L2 = new SexagesimalDegree(GeocentricLongitude.getValue() - 1.397 * T - 0.00031 * pow(T, 2));
            double incrLongitud2 = -0.09033 + 0.03916 * (cosine(L2) + sine(L2)) * tangent(GeocentricLatitude);//segundos
            double incrLatitud2 = 0.03916 * (cosine(L2) - sine(L2));

            GeocentricLongitude = GeocentricLongitude.plus(new SexagesimalDegree(incrLongitud / 3600));
            GeocentricLatitude = GeocentricLatitude.plus(new SexagesimalDegree(incrLatitud / 3600));

            SexagesimalDegree nutation = day.getNutationInLongitude();

            GeocentricLongitude = GeocentricLongitude.plus(new SexagesimalDegree(incrLongitud2 / 3600));
            GeocentricLatitude = GeocentricLatitude.plus(new SexagesimalDegree(incrLatitud2 / 3600));

            GeocentricLongitude = GeocentricLongitude.plus(nutation);

            return new Ecliptic(GeocentricLongitude, GeocentricLatitude).toEquatorial(day.getTrueObliquityEcliptic(CalculusType.PRECISE),distance);

        }
        else
        {
            throw new ProcessException("Only Mercury, Venus, Mars, Jupiter or Saturn");
        }
    }
    
    /**
     * 
     * @param epoch Julian day
     * @return Planet's apparent position in the Ecuatorial Coordinate System and apparent magnitude. 
     * Includes MERCURY, VENUS, MARS, JUPITER or SATURN
     * @throws ProcessException Only Mercury, Venus, Mars, Jupiter or Saturn 
     */
     public InfoPlanet getInfoPlanet(JulianDay epoch) throws ProcessException
    {
        if (planeta == MERCURY || planeta == VENUS || planeta == MARS || planeta == JUPITER || planeta == SATURN)
        {
            JulianDay day = new JulianDay(epoch);
            double error = 1e-5;

            SexagesimalDegree L = null;
            SexagesimalDegree B = null;
            double R = 0;
            double x = 0;
            double y = 0;
            double z = 0;

            SexagesimalDegree L0 = Heliocientric.getLongitude(EARTH, day);
            SexagesimalDegree B0 = Heliocientric.getLatitude(EARTH, day);
            double R0 = Heliocientric.getRadius(EARTH, day);
            double distance=0;

            for (int i = 0; i < 1000000; i++)
            {
                SexagesimalDegree L_ = Heliocientric.getLongitude(planeta, day);
                SexagesimalDegree B_ = Heliocientric.getLatitude(planeta, day);
                double R_ = Heliocientric.getRadius(planeta, day);

                x = R_ * cosine(B_) * cosine(L_) - R0 * cosine(B0) * cosine(L0);
                y = R_ * cosine(B_) * sine(L_) - R0 * cosine(B0) * sine(L0);
                z = R_ * sine(B_) - R0 * sine(B0);

                distance = sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2));
                double LightTime = 0.0057755183 * distance;

                if (L != null && B != null)
                {
                    double e = sqrt(pow(L.getValue() - L_.getValue(), 2) + pow(B.getValue() - B_.getValue(), 2) + pow(R - R_, 2));
                    if (e <= error)
                    {
                        break;
                    }
                }
                L = L_;
                B = B_;
                R = R_;
                day = new JulianDay(epoch.julianDate - LightTime);
            }

            SexagesimalDegree GeocentricLongitude = SexagesimalDegree.atan2(y, x);
            SexagesimalDegree GeocentricLatitude = SexagesimalDegree.atan2(z, sqrt(pow(x, 2) + pow(y, 2)));

            double T = day.getCenturiesSince2000();

            PlanetaryOrbitElements eo = PlanetaryOrbitElements.get(EARTH, day);
            double ex = eo.eccentricity;
            SexagesimalDegree pi = eo.perihelionLongitude;

            SexagesimalDegree SunLongitude = L0.plus(new SexagesimalDegree(180));            

            double K = 20.49552;
            double incrLongitud = (-K * cosine(SunLongitude.minus(GeocentricLongitude)) + ex * K * cosine(pi.minus(GeocentricLongitude))) / cosine(GeocentricLatitude); //segundos
            double incrLatitud = -K * sine(GeocentricLatitude) * sine(SunLongitude.minus(GeocentricLongitude)) - ex * sine(pi.minus(GeocentricLongitude));

            SexagesimalDegree L2 = new SexagesimalDegree(GeocentricLongitude.getValue() - 1.397 * T - 0.00031 * pow(T, 2));
            double incrLongitud2 = -0.09033 + 0.03916 * (cosine(L2) + sine(L2)) * tangent(GeocentricLatitude);//segundos
            double incrLatitud2 = 0.03916 * (cosine(L2) - sine(L2));

            GeocentricLongitude = GeocentricLongitude.plus(new SexagesimalDegree(incrLongitud / 3600));
            GeocentricLatitude = GeocentricLatitude.plus(new SexagesimalDegree(incrLatitud / 3600));

            SexagesimalDegree nutation = day.getNutationInLongitude();

            GeocentricLongitude = GeocentricLongitude.plus(new SexagesimalDegree(incrLongitud2 / 3600));
            GeocentricLatitude = GeocentricLatitude.plus(new SexagesimalDegree(incrLatitud2 / 3600));

            GeocentricLongitude = GeocentricLongitude.plus(nutation);
            double apparentMagnitude=0;
            
            
            SexagesimalDegree anguloFase = SexagesimalDegree.acos((R-R0*cosine(B)*cosine(L.minus(L0)))/distance);
            double fraccionIluminada = (1+cosine(anguloFase))/2;
            
            switch(planeta)
            {
                case MERCURY:
                    apparentMagnitude=-0.42+5*log10(R*distance)+0.0380*anguloFase.getValue()-0.000273*pow(anguloFase.getValue(),2)+0.000002*pow(anguloFase.getValue(),3);
                    apparentMagnitude=((int)(apparentMagnitude*10))/10.0;
                    break;
                    case VENUS:
                    apparentMagnitude=-4.40+5*log10(R*distance)+0.0009*anguloFase.getValue()+0.000239*pow(anguloFase.getValue(),2)-0.00000065*pow(anguloFase.getValue(),3);                   
                    apparentMagnitude=((int)(apparentMagnitude*10))/10.0;
                    break;
                    case MARS:
                    apparentMagnitude=-1.52+5*log10(R*distance)+0.016*anguloFase.getValue();
                   apparentMagnitude=((int)(apparentMagnitude*10))/10.0;
                    break;    
                    case JUPITER:
                    apparentMagnitude=-9.40+5*log10(R*distance)+0.005*anguloFase.getValue();
                    apparentMagnitude=((int)(apparentMagnitude*10))/10.0;
                    break;
                    case SATURN:
                    SexagesimalDegree inclinacionPlanoAnillo=new SexagesimalDegree(28.075216-0.012998*T+0.000004*pow(T,2));    
                    SexagesimalDegree longitudNodoAscendente=new SexagesimalDegree(169.508470+1.394681*T+0.000412*pow(T,2));
                    SexagesimalDegree latitudTierraASaturno = SexagesimalDegree.asin(sine(inclinacionPlanoAnillo)*cosine(GeocentricLatitude)*sine(GeocentricLongitude.minus(longitudNodoAscendente))-cosine(inclinacionPlanoAnillo)*sine(GeocentricLatitude));
                    SexagesimalDegree longitudNodoAscendenteOrbitaSAturno=new SexagesimalDegree(113.6655+0.8771*T);
                    SexagesimalDegree L_=L.minus(new SexagesimalDegree(0.01759/R));
                    SexagesimalDegree B_=B.minus(new SexagesimalDegree(0.000764*cosine(L.minus(longitudNodoAscendenteOrbitaSAturno))/R));
                    SexagesimalDegree latitudSolASaturno=SexagesimalDegree.asin(sine(inclinacionPlanoAnillo)*cosine(B_)*sine(L_.minus(longitudNodoAscendente))-cosine(inclinacionPlanoAnillo)*sine(B_));
                    SexagesimalDegree U1= SexagesimalDegree.atan2(sine(inclinacionPlanoAnillo)*sine(B_)+cosine(inclinacionPlanoAnillo)*cosine(B_)*sine(L_.minus(longitudNodoAscendente)), cosine(B_)*cosine(L_.minus(longitudNodoAscendente)));
                    SexagesimalDegree U2= SexagesimalDegree.atan2(sine(inclinacionPlanoAnillo)*sine(GeocentricLatitude)+cosine(inclinacionPlanoAnillo)*cosine(GeocentricLatitude)*sine(GeocentricLongitude.minus(longitudNodoAscendente)), cosine(GeocentricLatitude)*cosine(GeocentricLongitude.minus(longitudNodoAscendente)));
                    double AU=U1.getValue()-U2.getValue();
                    if(AU>180) AU=360-AU;                        
                    apparentMagnitude=-8.88+5*log10(R*distance)+0.044*AU-2.60*abs(sine(latitudTierraASaturno))+1.25*pow(sine(latitudTierraASaturno),2);
                    apparentMagnitude=((int)(apparentMagnitude*10))/10.0;
                    break;    
            }    

            return new InfoPlanet(new Ecliptic(GeocentricLongitude, GeocentricLatitude).toEquatorial(day.getTrueObliquityEcliptic(CalculusType.PRECISE),distance),apparentMagnitude);

        }
        else
        {
            throw new ProcessException("Only Mercury, Venus, Mars, Jupiter or Saturn");
        }
    }
    
}
