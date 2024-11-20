package datacenter;

import java.util.List;
import java.util.ArrayList;

public class Processor {

    /*
        Abstraction Function:
            this represents a processor with time capacity this.timeLimit
            and holds the jobs in this.jobs
        Representation Invariant:
            jobs != null && jobs does not contain nulls
            && timeLimit > 0
            && (there should be something more that you should think about)
     */

    private List<Job> jobs;
    private int timeLimit;

    /**
     * Create a new empty processor
     * @param timeLimit the limit on compute time on this processor, > 0
     */
    public Processor(int timeLimit) {
        this.timeLimit = timeLimit;
        this.jobs = new ArrayList<Job>();
    }

    /**
     * Check if a given job can fit in this processor
     *
     * @return true if adding the job does not exceed the time limit on this processor, and false otherwise.
     */
    public boolean canFitJob(Job job) {
        int sumoftime = 0;
        for(Job assignedJob : jobs) {
            sumoftime += assignedJob.getExecutionTime();
        }

        if((timeLimit - sumoftime) >= job.getExecutionTime()) {
            return true;
        }
        return false;
    }

    /** Inserts a job to the processor, at the end of its schedule
     *
     * @param job not null
     * @return true if the job can fit on this processor and was assigned, and false otherwise
     */
    public boolean addJob(Job job) {
        if(canFitJob(job)) {
            jobs.add(job);
            return true;
        }
        return false;
    }

    /** Get the peak memory usage of this processor
     *
     * @return the peak memory usage of the jobs ossigned to this processor
     * */
    public int getPeakMemoryUsage() {
        int maxMemory = 0;
        for(Job job : jobs) {
            maxMemory = Math.max(maxMemory, job.getMemoryUsage());
        }

        return maxMemory;
    }

    /** Get the total computation (execution) time of this processor
     *
     * @return the total computation (execution) time of jobs assigned
     * to this processor
     */
    public int getTotalComputationTime() {
        int totalComputationTime = 0;
        for(Job job : jobs) {
            totalComputationTime += job.getExecutionTime();
        }
        return totalComputationTime;
    }

    /** Check if this processor is equal to a given processor
     *
     * @return true if both processors have exactly the same jobs,
     * in the same order, and they have the same time limit
     */
    public boolean equals(Processor that) {
        if(this.timeLimit == that.timeLimit) {
            if(this.jobs.equals(that.jobs)) {
                return true;
            }
        }
        return false;
    }

    /** Get the time limit of this processor
     *
     * @return the time limit on this processor
     */
    public int getTimeLimit() {
        return this.timeLimit;
    }

    /**
     * Obtain the jobs scheduled on this processor, in scheduled order
     * @return the jobs scheduled on this processor, in scheduled order
     */
    public Job[] getJobs() {
        Job[] executingJobs = new Job[jobs.size()];
        List<Job> unvisitedjob = new ArrayList<>(jobs);
        for(int i = 0; i < jobs.size(); i++) {
            int executionTime = Integer.MAX_VALUE;
            for(Job job : unvisitedjob) {
                if(job.getExecutionTime() < executionTime) {
                    executionTime = job.getExecutionTime();
                    executingJobs[i] = job;
                }
            }
            unvisitedjob.remove(executingJobs[i]);
        }

        return executingJobs;
    }
}