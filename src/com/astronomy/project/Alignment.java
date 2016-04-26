/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.project;

import com.ProcessException;
import com.Global;
import com.astronomy.JulianDay;
import com.astronomy.coordinate_system.Equatorial;
import com.astronomy.coordinate_system.ApparentHorizontal;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jdom2.Element;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Alignment
{
   /**
    * Observatory site
    */
    private ReferencePoint observatory;
    /**
     * Foresight or reference horizontal point
     */
    private ReferencePoint foresight;
    /**
     * Direction
     */
    private Orientation orientation;
    /**
     * DEclination
     */
    private Declination declination;
    /**
     * XML tag for description
     */
    private Tag description;
    /**
     * XML tag for image path
     */
    private Tag imagenPath;

    SimpleStringProperty pObservatory;
    SimpleStringProperty pForesight;
    SimpleStringProperty pAzimuth;
    SimpleStringProperty pAltitude;
    SimpleStringProperty pDeclination;
    SimpleStringProperty pComments;

    public StringProperty imagenPathProperty()
    {
        return imagenPath.getValueProperty();
    }
    
    public StringProperty observatoryProperty()
    {
        return pObservatory;
    }

    public StringProperty foresightProperty()
    {
        return pForesight;
    }

    public StringProperty azimuthProperty()
    {
        return pAzimuth;
    }

    public StringProperty altitudeProperty()
    {
        return pAltitude;
    }

    public StringProperty declinationProperty()
    {
        return pDeclination;
    }

    public StringProperty commentsProperyt()
    {
        return pComments;
    }
    
      
    /**
     * 
     * @param obj Object
     * @return True if equal
     */
    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Alignment)) return false;
        Alignment a= (Alignment) obj;
        if(!this.observatory.equals(a.observatory)) return false;
        if(!this.foresight.equals(a.foresight)) return false;
        if(!this.orientation.equals(a.orientation)) return false;
        if(!this.declination.equals(a.declination)) return false;
        return true;
    }        

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.observatory);
        hash = 59 * hash + Objects.hashCode(this.foresight);
        hash = 59 * hash + Objects.hashCode(this.orientation);
        hash = 59 * hash + Objects.hashCode(this.declination);
        hash = 59 * hash + Objects.hashCode(this.description);
        hash = 59 * hash + Objects.hashCode(this.pObservatory);
        hash = 59 * hash + Objects.hashCode(this.pForesight);
        hash = 59 * hash + Objects.hashCode(this.pAzimuth);
        hash = 59 * hash + Objects.hashCode(this.pAltitude);
        hash = 59 * hash + Objects.hashCode(this.pDeclination);
        hash = 59 * hash + Objects.hashCode(this.pComments);
        return hash;
    }
    
    /**
     * 
     * @param alignmment Alignment to set 
     */
    public void set(Alignment alignmment)
    {
        this.observatory = alignmment.observatory;
        pObservatory.set(observatory.getName());
        this.foresight = alignmment.foresight;
        pForesight.set(foresight.getName());
        this.orientation = alignmment.orientation;
        pAzimuth.set(String.format("%.1f", orientation.getAzimuth().getSignedValue()).replace(",", "."));
        pAltitude.set(String.format("%.1f", orientation.getAltitude().getSignedValue()).replace(",", "."));
        this.description = alignmment.description;
        this.imagenPath = alignmment.imagenPath;
        this.declination=alignmment.declination;
        pDeclination.set(String.format("%.1f", getDeclination().getSignedValue()).replace(",", "."));        
    }        

    /**
     * 
     * @param observatory Observatory
     * @param foresight Foresight
     * @param orientation Orientation
     */
    public Alignment(ReferencePoint observatory, String foresight, ApparentHorizontal orientation)
    {
        this.observatory = observatory;
        pObservatory = new SimpleStringProperty(observatory.getName());
        this.foresight = new ReferencePoint(foresight, null, "referencia");
        pForesight = new SimpleStringProperty(foresight);
        this.orientation = new Orientation(orientation);
        pAzimuth = new SimpleStringProperty(String.format("%.1f", orientation.getAzimuth().getSignedValue()).replace(",", "."));
        pAltitude = new SimpleStringProperty(String.format("%.1f", orientation.getAltitude().getSignedValue()).replace(",", "."));

        this.description = new Tag("descripcion", "");
        this.imagenPath = new Tag("imagen", "");
        try
        {
            Equatorial e = orientation.toEquatorial(observatory.getCoordinates().getLatitude(), observatory.getCoordinates().getLongitude(), JulianDay.valueOf("1/1/-2000"));
            declination = new Declination(e.getDeclination());
            pDeclination = new SimpleStringProperty(String.format("%.1f", getDeclination().getSignedValue()).replace(",", "."));
        }
        catch (ProcessException ex)
        {
            Global.info.log(ex);
        }

    }

    /**
     * 
     * @param observatory Observatory
     * @param foresight Foresight
     */
    public Alignment(ReferencePoint observatory, ReferencePoint foresight)
    {
        this.observatory = observatory;
        pObservatory = new SimpleStringProperty(observatory.getName());
        this.foresight = foresight;
        pForesight = new SimpleStringProperty(foresight.getName());
        this.description = new Tag("descripcion", "");
        this.imagenPath = new Tag("imagen", "");
        try
        {
            this.orientation = new Orientation(ApparentHorizontal.valueOf(observatory.getCoordinates(), foresight.getCoordinates()));
            Equatorial e = this.orientation.toEquatorial(observatory.getCoordinates().getLatitude(), observatory.getCoordinates().getLongitude(), JulianDay.valueOf("1/1/-2000"));
            declination = new Declination(e.getDeclination());
            pAzimuth = new SimpleStringProperty(String.format("%.1f", orientation.getAzimuth().getSignedValue()).replace(",", "."));
            pAltitude = new SimpleStringProperty(String.format("%.1f", orientation.getAltitude().getSignedValue()).replace(",", "."));
            pDeclination = new SimpleStringProperty(String.format("%.1f", getDeclination().getSignedValue()).replace(",", "."));
        }
        catch (ProcessException ex)
        {
            Logger.getLogger(Alignment.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * 
     * @param e XML element
     * @throws ProcessException Format error
     */
    public Alignment(Element e) throws ProcessException
    {
        pComments=new SimpleStringProperty();
        for (Element el : e.getChildren())
        {
            switch (el.getName())
            {
                case "lugar":
                    if (el.getAttributeValue("tipo").equals("origen"))
                    {
                        observatory = new ReferencePoint(el);
                        pObservatory = new SimpleStringProperty(observatory.getName());
                    }
                    else if (el.getAttributeValue("tipo").equals("referencia"))
                    {
                        foresight = new ReferencePoint(el);
                        pForesight = new SimpleStringProperty(foresight.getName());
                    }
                    break;
                case "direccion":
                    orientation = new Orientation(el);
                    pAzimuth = new SimpleStringProperty(String.format("%.1f", orientation.getAzimuth().getSignedValue()).replace(",", "."));
                    pAltitude = new SimpleStringProperty(String.format("%.1f", orientation.getAltitude().getSignedValue()).replace(",", "."));
                    break;
                case "descripcion":
                    description = new Tag("descripcion", el);
                    pComments.set(description.getValue());
                    break;
                 case "imagen":
                    imagenPath = new Tag("imagen", el);                    
                    break;   
                case "declinacion":
                    declination = new Declination(el);
                    pDeclination = new SimpleStringProperty(String.format("%.1f", getDeclination().getSignedValue()).replace(",", "."));
                    break;
            }
        }
        if(imagenPath==null) imagenPath = new Tag("imagen", "");  
    }

    /**
     * 
     * @return XML element
     */
    public Element getXMLElement()
    {
        Element raiz = new Element("alineamiento");
        raiz.addContent(observatory.getElementoXML());
        raiz.addContent(foresight.getElementoXML());
        raiz.addContent(orientation.getXMLElement());
        raiz.addContent(description.getXMLElement());
        raiz.addContent(imagenPath.getXMLElement());
        raiz.addContent(getDeclination().getElementoXML());
        return raiz;
    }

    /**
     * @return observatory coordinates
     */
    public ReferencePoint getObservatory()
    {
        return observatory;
    }

    /**
     * @return foresight coordinates
     */
    public ReferencePoint getForesight()
    {
        return foresight;
    }

    /**
     * @return orientation
     */
    public ApparentHorizontal getOrientation()
    {
        return orientation;
    }

    /**
     * @return comments
     */
    public String getComments()
    {
        return description.getValue();
    }
    
    /**
     * 
     * @param text Text to set as comments
     */
    public void setComments(String text)
    {
        description.setValue(text);
        pComments.set(text);        
        
    } 

    /**
     * @return declination
     */
    public Declination getDeclination()
    {
        return declination;
    }

    /**
     * @return image path
     */
    public String getImagenPath()
    {
        return imagenPath.getValue();
    }
    /**
     * 
     * @param path Image path to set
     */
    public void setImagenPath(String path)
    {
        imagenPath.setValue(path);
    } 
    
           
}
