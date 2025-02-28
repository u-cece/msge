package pet.cece;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Stack;

public class Profiler {

    private static final Logger LOGGER = LoggerFactory.getLogger("profiler");

    private final Stack<String> stack = new Stack<>();

    public Profiler() {
    }

    public void push(String stage) {
        stack.push(stage);
        LOGGER.debug("Stage started: {}", stage);
    }

    public void pop() {
        LOGGER.debug("Stage ended: {}", stack.peek());
        stack.pop();
    }

    public Optional<String> getStage() {
        return Optional.ofNullable(stack.peek());
    }

    public void popPush(String stage) {
        pop();
        push(stage);
    }
}
