package planetlotto.domain.winning;

import java.util.Arrays;

/**
 * 이 클래스는 로또 번호가 당첨 번호, 보너스 번호를 맞춘 개수에 따라 가지는 당첨 금액 정보를 나타낸다.
 *
 * <p>각 Enum value는 {@code int} 당첨 번호(5개) 중 맞춘 개수, {@code boolean} 보너스 번호 적중 여부, {@code int} 당첨 금액을 가진다.
 */

public enum WinningInformation {
    FIVE_MATCHED(1, 5, false, 100_000_000),
    FOUR_MATCHED_WITH_BONUS(2, 4, true, 10_000_000),
    FOUR_MATCHED(3, 4, false, 1_500_000),
    THREE_MATCHED_WITH_BONUS(4, 3, true, 500_000),
    TWO_MATCHED_WITH_BONUS(5, 2, true, 5_000),
    EMPTY(0, 0, false, 0),
    ;

    private static final int MIN_MATCH_COUNT = 0;
    private static final int MAX_MATCH_COUNT = 5;

    private final int rank;
    private final int matchCount;
    private final boolean bonusMatched;
    private final int prizeMoney;

    WinningInformation(int rank, int matchCount, boolean bonusMatched, int prizeMoney) {
        this.rank = rank;
        this.matchCount = matchCount;
        this.bonusMatched = bonusMatched;
        this.prizeMoney = prizeMoney;
    }

    public static WinningInformation findByMatchCountAndBonusMatched(int matchCount,
                                                                     boolean bonusMatched) {
        validateMatchCount(matchCount);

        if (matchCount == 4 && bonusMatched) {
            return FOUR_MATCHED_WITH_BONUS;
        }

        return Arrays.stream(WinningInformation.values())
                .filter(winningInfo -> winningInfo.matchCount == matchCount)
                .filter(winningInfo -> winningInfo.bonusMatched == bonusMatched)
                .findFirst()
                .orElse(EMPTY);
    }

    private static void validateMatchCount(int matchCount) {
        if (matchCount < MIN_MATCH_COUNT || matchCount > MAX_MATCH_COUNT) {
            throw new IllegalArgumentException("일치 개수는 " + MIN_MATCH_COUNT + "이상, " + MAX_MATCH_COUNT + "이하입니다.");
        }
    }

    public int getPrizeMoney() {
        return prizeMoney;
    }

    public int toRank() {
        return rank;
    }
}
