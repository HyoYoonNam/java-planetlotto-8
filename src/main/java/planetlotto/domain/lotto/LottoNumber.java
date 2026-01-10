package planetlotto.domain.lotto;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LottoNumber implements Comparable<LottoNumber> {
    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 30;
    private static final String OUT_OF_RANGED_ERROR_MESSAGE =
            "로또 번호는 " + MIN_NUMBER + "부터 " + MAX_NUMBER + " 사이의 숫자여야 합니다.";
    private static final Map<Integer, LottoNumber> CACHE =
            IntStream.rangeClosed(MIN_NUMBER, MAX_NUMBER)
                    .boxed()
                    .collect(Collectors.toMap(Function.identity(), LottoNumber::new));


    private final int number;

    private LottoNumber(int number) {
        this.number = number;
    }

    public static LottoNumber from(int number) {
        requireNonOutOfRange(number);
        return CACHE.get(number);
    }

    private static void requireNonOutOfRange(int number) {
        if (number < MIN_NUMBER || number > MAX_NUMBER) {
            throw new IllegalArgumentException(OUT_OF_RANGED_ERROR_MESSAGE);
        }
    }

    @Override
    public int compareTo(LottoNumber otherLottoNumber) {
        return Integer.compare(number, otherLottoNumber.number);
    }

    public int getNumber() {
        return number;
    }
}
