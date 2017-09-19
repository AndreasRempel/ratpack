/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ratpack.dropwizard.metrics;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import java.io.StringWriter;

/**
 * A Handler that exposes metric reports in Prometheus format.
 * <p>
 * This handler should be bound to an application path, and most likely only for the GET method…
 * <pre class="java-chain-dsl">
 * import ratpack.dropwizard.metrics.MetricsPrometheusHandler;
 * import static org.junit.Assert.*;
 *
 * assertTrue(chain instanceof ratpack.handling.Chain);
 * chain.get("admin/metrics", new MetricsPrometheusHandler());
 * </pre>
 *
 * @since 1.6
 */
public class MetricsPrometheusHandler implements Handler {

  @Override
  public void handle(Context ctx) throws Exception {
    ctx.getResponse().contentType(TextFormat.CONTENT_TYPE_004);
    ctx.getResponse().status(200);
    StringWriter sw = new StringWriter();
    TextFormat.write004(sw, ctx.get(CollectorRegistry.class).metricFamilySamples());
    ctx.render(sw.toString());
  }
}
