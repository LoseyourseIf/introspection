package xingyu.lu.lab.tests;

import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * @Description:
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-07-11  16:13
 */
@Data
@Builder
public class FakeCoinProblem {

    public List<Coin> coinList;
    public static int rounds = 0;

    @Data
    @Builder
    public static class Coin implements Comparable<Coin> {
        private int weight;

        @Override
        public int compareTo(Coin o) {
            return Integer.compare(this.getWeight(), o.getWeight());
        }
    }

    private void buildFakeCoin() {
        coinList = new ArrayList<>();
        Random random = new Random();
        int fakeCoinWeight = random.nextInt(10) + 1;
        int realCoinWeight = random.nextInt(90) + 10;
        int coinListSize = random.nextInt(50) + 50;
        int fakeCoinIndex = random.nextInt(coinListSize);
        for (int i = 0; i < coinListSize; i++) {
            if (i == fakeCoinIndex) {
                coinList.add(Coin.builder().weight(fakeCoinWeight).build());
            } else {
                coinList.add(Coin.builder().weight(realCoinWeight).build());
            }
        }
        System.out.println("fakeCoinWeight = " + fakeCoinWeight);
        System.out.println("realCoinWeight = " + realCoinWeight);
        System.out.println("coinListSize = " + coinList.size());
    }

    private Coin findFakeCoin(List<Coin> coinList) {
        rounds++;
        System.out.println("rounds  = " + rounds);
        System.out.println("coinListSize = " + coinList.size());
        int listSize = coinList.size();
        if (1 == listSize) {
            return coinList.stream().findFirst().get();
        } else if (2 == listSize) {
            return coinList.stream()
                    .min(Comparator
                            .comparingInt(Coin::getWeight)).get();
        }

        int index = 0;
        int partition = coinList.size() / 3;
        int remain = coinList.size() % 3;
        int lastPartition = partition + remain;

        List<Coin> subListA = coinList.subList(index, index + partition);
        index += partition;
        List<Coin> subListB = coinList.subList(index, index + partition);
        index += partition;
        List<Coin> subListC = coinList.subList(index, index + lastPartition);

        int subListAWeight;
        int subListBWeight;

        subListAWeight = subListA.stream().mapToInt(Coin::getWeight).sum();
        subListBWeight = subListB.stream().mapToInt(Coin::getWeight).sum();

        if (subListAWeight == subListBWeight) {
            return findFakeCoin(subListC);
        } else {
            return subListAWeight < subListBWeight ?
                    findFakeCoin(subListA) :
                    findFakeCoin(subListB);
        }

    }

    public static void main(String[] args) {
        FakeCoinProblem problem = FakeCoinProblem.builder().build();
        problem.buildFakeCoin();
        FakeCoinProblem.Coin coin = problem.findFakeCoin(problem.coinList);
        assert coin != null;
        System.out.println("FakeCoin = " + JSON.toJSONString(coin));
    }


}
