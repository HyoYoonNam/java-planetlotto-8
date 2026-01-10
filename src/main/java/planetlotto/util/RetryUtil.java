package planetlotto.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class RetryUtil {
    private RetryUtil() {
    }

    /**
     * 예외가 발생하지 않을 때까지 supplier를 반복 실행한다.
     * 예외 발생 시 onException(Consumer)을 실행하고 재시도한다.
     */
    public static <T> T retryUntilSuccess(Supplier<T> supplier, Consumer<String> onException) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                onException.accept(e.getMessage());
            }
        }
    }
}
