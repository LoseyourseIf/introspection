package xingyu.lu.lab.ynii.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class LadderPricingTest {

    @Data
    @NoArgsConstructor
    static class Ladder {
        public final BigDecimal ladderDistance = new BigDecimal("10");
        private BigDecimal ladder;
        private BigDecimal unitPrice;
        private BigDecimal quickPrice;

        public Ladder(BigDecimal ladder, BigDecimal unitPrice) {
            this.ladder = ladder;
            this.unitPrice = unitPrice;
            this.quickPrice = this.ladderDistance.multiply(this.unitPrice);
        }
    }

    @Test
    public void bigTest() {
        BigDecimal a = new BigDecimal("3");
        BigDecimal b = new BigDecimal("10");

        System.out.println(a.remainder(b).toPlainString());
    }

    @Test
    public void testLadder() {

        List<Ladder> ladderList = buildLadderList();
        Random random = new Random();

        BigDecimal mileage = new BigDecimal(random.nextInt(100));

        System.out.println(mileage);

        Ladder firstLadder = ladderList.get(0);

        if (mileage.compareTo(firstLadder.ladder) < 0) {
            System.out.println("Total : " + mileage.multiply(firstLadder.unitPrice).toPlainString());
            return;
        }

        List<Ladder> matchedLadder =
                ladderList.stream()
                        .filter(s -> mileage.compareTo(s.getLadder()) >= 0)
                        .sorted(Comparator.comparing(Ladder::getLadder))
                        .collect(Collectors.toList());

        System.out.println(JSON.toJSONString(matchedLadder, SerializerFeature.PrettyFormat));

        Ladder lastLadder = matchedLadder.get(matchedLadder.size() - 2);
        System.out.println("LastLadder : "+lastLadder.toString());

        Ladder overLadder = matchedLadder.get(matchedLadder.size() - 1);
        System.out.println("OverLadder : " +overLadder.toString());

        matchedLadder.remove(overLadder);

        BigDecimal matchedLadderPrice = new BigDecimal(0);

        for (Ladder l : matchedLadder) {
            System.out.println("Ladder : " + l.getLadder());
            matchedLadderPrice = matchedLadderPrice.add(l.getQuickPrice());
        }

        BigDecimal over = mileage.subtract(lastLadder.getLadder()).multiply(overLadder.getUnitPrice());

        BigDecimal total = matchedLadderPrice.add(over);

        System.out.println("Total : " + total.toPlainString());

    }

    private List<Ladder> buildLadderList() {

        List<Ladder> ladderList = new ArrayList<>();

        ladderList.add(new Ladder(new BigDecimal("30"), new BigDecimal("2.0")));
        ladderList.add(new Ladder(new BigDecimal("40"), new BigDecimal("2.5")));
        ladderList.add(new Ladder(new BigDecimal("10"), new BigDecimal("0.5")));
        ladderList.add(new Ladder(new BigDecimal("20"), new BigDecimal("1.5")));

        return ladderList.stream().sorted(Comparator.comparing(Ladder::getLadder))
                .collect(Collectors.toList());
    }

}
