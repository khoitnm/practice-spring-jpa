package org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.common.optn_telemetry;

import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.logging.LoggingSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenTelemetryConfig {

    @Bean
    public Tracer tracer() {
        // Create a Logging exporter that prints spans to the console
        LoggingSpanExporter loggingExporter = new LoggingSpanExporter();

        // Create tracer provider and register span processor
        SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
            .addSpanProcessor(SimpleSpanProcessor.create(loggingExporter))
            .build();

        // Create OpenTelemetry SDK instance
        OpenTelemetrySdk openTelemetrySdk = OpenTelemetrySdk.builder()
            .setTracerProvider(tracerProvider)
            .buildAndRegisterGlobal();

        // Return tracer for instrumentation
        return openTelemetrySdk.getTracer("spring-boot-example");
    }
}