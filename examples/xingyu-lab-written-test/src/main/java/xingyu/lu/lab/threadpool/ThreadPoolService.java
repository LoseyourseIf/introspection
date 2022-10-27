package xingyu.lu.lab.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author lxy
 */
public class ThreadPoolService {

    private static final ThreadFactory NAMED_THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("OPENDATA_TASK-POOL-%d").build();

    /**
     * 阿⾥巴巴的JAVA开发⼿册推荐⽤ThreadPoolExecutor创建线程池
     * <p>
     * corePoolSize    线程池中线程数量的⼤⼩（即使线程是空闲的，也要保留在池中的线程数）
     * maximumPoolSize 线程池中允许的最⼤线程数量
     * keepAliveTime   当线程数⼤于核⼼时，此为终⽌前多余的空闲线程等待新任务的最长时间
     * unit            keepAliveTime 的时间单位
     * workQueue       ⽤来储存等待执⾏任务的队列
     * threadFactory   创建线程的⼯⼚类
     * handler         拒绝策略类,当线程池数量达到上线并且workQueue队列长度达到上限时就需要对到来的任务做拒绝处理
     * 原理：
     * 有请求时，创建线程执⾏任务，当线程数量等于corePoolSize时，请求加⼊阻塞队列⾥，当队列满了时，接着创建线程，
     * 线程数等于maximumPoolSize。当任务处理不过来的时候，线程池开始执⾏拒绝策略。
     * 换⾔之，线程池最多同时并⾏执⾏maximumPoolSize的线程，最多处理maximumPoolSize+workQueue.size()的任务。多余的默认采⽤AbortPolicy会丢弃。
     * 阻塞队列：
     * ArrayBlockingQueue ：⼀个由数组结构组成的有界阻塞队列。
     * LinkedBlockingQueue ：⼀个由链表结构组成的有界阻塞队列。
     * PriorityBlockingQueue ：⼀个⽀持优先级排序的⽆界阻塞队列。
     * DelayQueue：⼀个使⽤优先级队列实现的⽆界阻塞队列。
     * SynchronousQueue：⼀个不存储元素的阻塞队列。
     * LinkedTransferQueue：⼀个由链表结构组成的⽆界阻塞队列。
     * LinkedBlockingDeque：⼀个由链表结构组成的双向阻塞队列。
     * 　　拒绝策略：
     * ThreadPoolExecutor.AbortPolicy: 丢弃任务并抛出RejectedExecutionException异常。 (默认)
     * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
     * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前⾯的任务，然后重新尝试执⾏任务。（重复此过程）
     * ThreadPoolExecutor.CallerRunsPolicy：由调⽤线程处理该任务。
     */
    private static final ExecutorService SERVICE = new ThreadPoolExecutor(
            10,
            20,
            30,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(50),
            NAMED_THREAD_FACTORY,
            new ThreadPoolExecutor.DiscardPolicy()
    );

    public static ExecutorService getService() {
        return SERVICE;
    }

    public static void executeRunnable(Runnable runnable) {
        SERVICE.execute(runnable);
    }
}
