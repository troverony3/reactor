package com.company;/// по сути лист реакторов))


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="ReactorType") //Аннотация для того чтобы в xml все хорошо открывалось,
@XmlAccessorType(XmlAccessType.FIELD)
public class Reactortype {
    @XmlElement(name = "TYPE")
    private List<Reactor> reactortype;

    public Reactortype(){       //пустой конструктор только создает объект
    }

    public List<Reactor> getReactortype() {
        return reactortype;
    }

    public void setReactortype(List<Reactor> reactortype) {
        this.reactortype = reactortype;
    }
}