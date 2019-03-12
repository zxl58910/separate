package com.mapscience.modular.system.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotations.TableField;
import com.mapscience.modular.system.model.Employee;

public class EmployeeDTO extends Employee {
    private static final long serialVersionUID = -4965143920295563042L;

    @Excel(name = "公司名称")
    @TableField("company_id")
    private String orgName;

    @Excel(name = "岗位层级")
    @TableField("post_level_name")
    private String postLevel;

    @Excel(name = "岗位类别")
    @TableField("post_type_name")
    private String postType;

    @Excel(name = "现任职务")
    @TableField("post_nam")
    private String currentPosition;

    @Excel(name = "奖惩情况")
    @TableField("award")
    private String awards;

    @Excel(name = "年度考核结果")
    @TableField("annual_assessment_results")
    private String annualAssessmentResults;

    @Excel(name = "档案所在地")
    @TableField("archives_residence")
    private String archivesResidence;

    @Excel(name = "专业")
    @TableField("major")
    private String major;

    @Excel(name = "学位类型")
    @TableField("degree_type_name")
    private String degreeTypeName;

    @Excel(name = "起始时间")
    @TableField("start_time")
    private String startTime;

    @Excel(name = "结束时间")
    @TableField("leave_time")
    private String leaveTime;

    @Excel(name = "所在公司")
    @TableField("work_history_name")
    private String workHistoryName;

    @Excel(name = "岗位")
    @TableField("post")
    private String post;

    @Excel(name = "称谓")
    @TableField("call")
    private String call;

    @Excel(name = "姓名")
    @TableField("name")
    private String name;

    @Excel(name = "出生日期")
    @TableField("borth_date")
    private String borthDate;

    @Excel(name = "政治面貌")
    @TableField("political_look")
    private String politicalLook;

    @Excel(name = "工作单位及职务")
    @TableField("work_unit")
    private String workUnit;


    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDegreeTypeName() {
        return degreeTypeName;
    }

    public void setDegreeTypeName(String degreeTypeName) {
        this.degreeTypeName = degreeTypeName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getWorkHistoryName() {
        return workHistoryName;
    }

    public void setWorkHistoryName(String workHistoryName) {
        this.workHistoryName = workHistoryName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBorthDate() {
        return borthDate;
    }

    public void setBorthDate(String borthDate) {
        this.borthDate = borthDate;
    }

    public String getPoliticalLook() {
        return politicalLook;
    }

    public void setPoliticalLook(String politicalLook) {
        this.politicalLook = politicalLook;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPostLevel() {
        return postLevel;
    }

    public void setPostLevel(String postLevel) {
        this.postLevel = postLevel;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getAnnualAssessmentResults() {
        return annualAssessmentResults;
    }

    public void setAnnualAssessmentResults(String annualAssessmentResults) {
        this.annualAssessmentResults = annualAssessmentResults;
    }

    public String getArchivesResidence() {
        return archivesResidence;
    }

    public void setArchivesResidence(String archivesResidence) {
        this.archivesResidence = archivesResidence;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "orgName='" + orgName + '\'' +
                ", postLevel='" + postLevel + '\'' +
                ", postType='" + postType + '\'' +
                ", currentPosition='" + currentPosition + '\'' +
                ", awards='" + awards + '\'' +
                ", annualAssessmentResults='" + annualAssessmentResults + '\'' +
                ", archivesResidence='" + archivesResidence + '\'' +
                ", major='" + major + '\'' +
                ", degreeTypeName='" + degreeTypeName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", leaveTime='" + leaveTime + '\'' +
                ", workHistoryName='" + workHistoryName + '\'' +
                ", post='" + post + '\'' +
                ", call='" + call + '\'' +
                ", name='" + name + '\'' +
                ", borthDate='" + borthDate + '\'' +
                ", politicalLook='" + politicalLook + '\'' +
                ", workUnit='" + workUnit + '\'' +
                '}';
    }
}
