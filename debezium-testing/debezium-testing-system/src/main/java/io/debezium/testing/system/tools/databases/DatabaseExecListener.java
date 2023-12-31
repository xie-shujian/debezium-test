/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.testing.system.tools.databases;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.client.dsl.ExecListener;

public class DatabaseExecListener implements ExecListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInitListener.class);

    private final String name;
    private final CountDownLatch latch;

    public DatabaseExecListener(String name, CountDownLatch latch) {
        this.name = name;
        this.latch = latch;
    }

    @Override
    public void onOpen() {
        // do nothing
    }

    @Override
    public void onFailure(Throwable t, Response failureResponse) {
        LOGGER.info("Executor on " + name + " failed");
        latch.countDown();
    }

    @Override
    public void onClose(int code, String reason) {
        LOGGER.info("Executor on " + name + " closed");
        latch.countDown();
    }
}
