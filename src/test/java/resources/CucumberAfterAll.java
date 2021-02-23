package resources;

import Steps.hooks;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;

public class CucumberAfterAll implements ConcurrentEventListener {

    private EventHandler<TestRunStarted> beforeAll = event -> {
        // something that needs doing before everything
    };
    private EventHandler<TestRunFinished> afterAll = event -> {
        hooks.closeDriver();
    };

    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestRunStarted.class, beforeAll);
        eventPublisher.registerHandlerFor(TestRunFinished.class, afterAll);
    }
}
