package org.amplexus.demo.loadtest.samplers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

/**
 *  * A load test sampler for the order creation web service.
 *   */
public class DemoSampler extends AbstractJavaSamplerClient {
    Logger logger = Logger.getLogger(DemoSampler.class.getName());

    @Override
    public SampleResult runTest(JavaSamplerContext arg0) {
        SampleResult results = new SampleResult();

        results.sampleStart();
        results.samplePause();

        // Do some time consuming setup here...

        results.sampleResume();

        // Measuring starts from here...

        try {
            Thread.sleep(200);
            results.setSuccessful(false);
        } catch (InterruptedException e) {
            results.setSampleLabel("DemoSampler: interrupted: " + e.getClass().getName() + ": " + e.getMessage());
            results.setResponseCode("500");
            results.setResponseMessage(e.getMessage());
            results.setSuccessful(false);
        } catch (RuntimeException e) {
            results.setSampleLabel("DemoSampler: failed: " + e.getClass().getName() + ": " + e.getMessage());
            results.setResponseCode("500");
            results.setResponseMessage(e.getMessage());
            results.setSuccessful(false);
            logger.log(Level.SEVERE, "Error calling service", e);
        }

        results.sampleEnd();
        return results;
    }
}
