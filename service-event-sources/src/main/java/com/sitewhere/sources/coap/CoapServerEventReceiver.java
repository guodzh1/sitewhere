/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.sources.coap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.californium.core.network.config.NetworkConfig;

import com.sitewhere.sources.InboundEventReceiver;
import com.sitewhere.sources.spi.IInboundEventReceiver;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.server.lifecycle.ILifecycleProgressMonitor;

/**
 * Implementation of {@link IInboundEventReceiver} that starts a CoAP server
 * using the Eclipse Californium implementation.
 * 
 * @author Derek
 */
public class CoapServerEventReceiver extends InboundEventReceiver<byte[]> {

    /** Static logger instance */
    private static Log LOGGER = LogFactory.getLog(CoapServerEventReceiver.class);

    /** Supplies standard CoAP port */
    private static final int COAP_PORT = NetworkConfig.getStandard().getInt(NetworkConfig.Keys.COAP_PORT);

    /** Default hostname */
    private static final String DEFAULT_HOSTNAME = "localhost";

    /** Hostname for binding socket */
    private String hostname = DEFAULT_HOSTNAME;

    /** Port for binding socket */
    private int port = COAP_PORT;

    /** Customized SiteWhere CoAP server */
    private SiteWhereCoapServer server;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sitewhere.server.lifecycle.LifecycleComponent#start(com.sitewhere.spi
     * .server.lifecycle.ILifecycleProgressMonitor)
     */
    @Override
    public void start(ILifecycleProgressMonitor monitor) throws SiteWhereException {
	if (server == null) {
	    server = new SiteWhereCoapServer(this, getHostname(), getPort());
	}
	server.start();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sitewhere.server.lifecycle.LifecycleComponent#stop(com.sitewhere.spi.
     * server.lifecycle.ILifecycleProgressMonitor)
     */
    @Override
    public void stop(ILifecycleProgressMonitor monitor) throws SiteWhereException {
	server.stop();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.server.lifecycle.ILifecycleComponent#getLogger()
     */
    @Override
    public Log getLogger() {
	return LOGGER;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.communication.IInboundEventReceiver#
     * getDisplayName()
     */
    @Override
    public String getDisplayName() {
	return "coap:" + getHostname();
    }

    public SiteWhereCoapServer getServer() {
	return server;
    }

    public void setServer(SiteWhereCoapServer server) {
	this.server = server;
    }

    public String getHostname() {
	return hostname;
    }

    public void setHostname(String hostname) {
	this.hostname = hostname;
    }

    public int getPort() {
	return port;
    }

    public void setPort(int port) {
	this.port = port;
    }
}