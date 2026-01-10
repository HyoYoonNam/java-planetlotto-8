package planetlotto;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.util.List;
import org.junit.jupiter.api.Test;

class ApplicationTest extends NsTest {
    private static final String ERROR_MESSAGE = "[ERROR]";

    @Test
    void 기능_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("1000", "1,2,3,4,5", "6");
                    assertThat(output()).contains(
                            "2개를 구매했습니다.",
                            "[8, 11, 13, 21, 22]",
                            "[1, 3, 6, 14, 22]",
                            "당첨 통계",
                            "5개 일치 (100,000,000원) - 0개",
                            "4개 일치, 보너스 번호 일치 (10,000,000원) - 0개",
                            "4개 일치 (1,500,000원) - 0개",
                            "3개 일치, 보너스 번호 일치 (500,000원) - 0개",
                            "2개 일치, 보너스 번호 일치 (5,000원) - 1개",
                            "0개 일치 (0원) - 1개"
                    );
                },
                List.of(8, 11, 13, 21, 22),
                List.of(1, 3, 6, 14, 22)
        );
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() -> {
            runException("500j");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
