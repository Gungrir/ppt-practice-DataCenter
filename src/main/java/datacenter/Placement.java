package datacenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Placement {
    private List<Processor> processors;

    /** Create a new empty placement
     *
     */
    public Placement() {
        this.processors = new ArrayList<Processor>();
    }

    /** Add a processor to this placement
     *
     */
    public void addProcessor(Processor processor) {
        this.processors.add(processor);
    }

    /** Get the cost of this placement
     *
     *  @return cost
     */
    public int getCost() {
        int cost = 0;
        for(Processor processor : processors) {
            cost += processor.getPeakMemoryUsage();
        }
        return cost;
    }

    /**
     * Compute the makespan of this placement
     *
     * @return the makespan of the placement, and return 0, if there is no work
     * (no processors or no jobs on any processor)
     */
    public int getMakeSpan() {
        int makespan = 0;
        for(Processor processor : processors) {
            makespan = Math.max(makespan, processor.getTotalComputationTime());
        }
        return makespan;
    }

    /** Check if this placement is equal to another given placement
     *
     * @param that is the other placement to check
     * @return true if (1) number of processors in "that" is
     * equal to the number of processors in "this", and (2) each processor
     * in "this" is equal to the corresponding processor in "that"
     * (order of processors does matter)
     * */
    public boolean equals(Placement that) {
        if(this.processors.size() == that.processors.size()) {
            if(this.processors.equals(that.processors)) {
                return true;
            }
        }
        return false;
    }

    /** Obtain the mean flow time for this placement
     *
     * @return the mean flow time, and return 0 if there is no work (no processors or no jobs on any processor)
     */
    public double getMeanFlowTime() {
        double totalFlowTime = 0.0;
        double totalJobs = 0.0;
        for(Processor processor : processors) {
            int time=0;
            for(Job job : processor.getJobs()) {
                time += job.getExecutionTime();
                totalFlowTime += time;
                totalJobs++;
            }
        }
        return totalFlowTime /totalJobs;
    }

    /** Obtain the median flow time for this placement
     *
     * @return the median flow time, and return 0 if there is no work (no processors or no jobs on any processors)
     */
    public double getMedianFlowTime() {
        double medianFlowTime = 0.0;
        List<Integer> flowTimes = new ArrayList<>();
        for(Processor processor : processors) {
            int time=0;
            for(Job job : processor.getJobs()) {
                time += job.getExecutionTime();
                flowTimes.add(time);
            }
        }
        Collections.sort(flowTimes);
        if(flowTimes.size() % 2 == 1) {
            medianFlowTime = flowTimes.get(flowTimes.size()/2);
        }
        else{
            medianFlowTime = (flowTimes.get(flowTimes.size()/2) + flowTimes.get(flowTimes.size()/2 + 1))/2.0;
        }
        return medianFlowTime;
    }

}