package de.spiegel.timeregistration.business.monitoring.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author veithi
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CallEvent {

    private String methodName;
    private long dration;

    public CallEvent() {
    }

    public CallEvent(String methodName, long dration) {
        this.methodName = methodName;
        this.dration = dration;
    }

    public String getMethodName() {
        return methodName;
    }

    public long getDration() {
        return dration;
    }

    @Override
    public String toString() {
        return "CallEvent{" + "methodName=" + methodName + ", dration=" + dration + '}';
    }

}
