package de.spiegel.timeregistration.business.logging.boundary;

/**
 *
 * @author veithi
 */
@FunctionalInterface
public interface LogSink {

    void log(String msg);
}
