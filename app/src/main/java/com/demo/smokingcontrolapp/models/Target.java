package com.demo.smokingcontrolapp.models;

public class Target {
    private String targetName;
    private Long startDate;
    private Long endDate;
    private int countOfCigarette;
    private int countOfCigaretteSmoked;

    public Target() {
    }

    public Target(String targetName, Long startDate, Long endDate, int countOfCigarette, int countOfCigaretteSmoked) {
        this.targetName = targetName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.countOfCigarette = countOfCigarette;
        this.countOfCigaretteSmoked = countOfCigaretteSmoked;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public int getCountOfCigarette() {
        return countOfCigarette;
    }

    public void setCountOfCigarette(int countOfCigarette) {
        this.countOfCigarette = countOfCigarette;
    }

    public int getCountOfCigaretteSmoked() {
        return countOfCigaretteSmoked;
    }

    public void setCountOfCigaretteSmoked(int countOfCigaretteSmoked) {
        this.countOfCigaretteSmoked = countOfCigaretteSmoked;
    }

    @Override
    public String toString() {
        return "Target{" +
                "targetName='" + targetName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", countOfCigarette=" + countOfCigarette +
                ", countOfCigaretteSmoked=" + countOfCigaretteSmoked +
                '}';
    }
}
