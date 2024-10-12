package xyz.kbws.domain.model.vo;

import java.util.Map;

/**
 * @author kbws
 * @date 2024/10/5
 * @description:
 */
public class ProjectConfigVO {
    private String _groupId;
    private String _artifactId;
    private String _version;
    private String _package;
    private Boolean web = false;
    private Boolean mysql = false;
    private Boolean redis = false;
    private Boolean freemarker = false;
    private Boolean devtools = false;
    private Boolean configurationProcessor = false;
    private Boolean aop = false;
    private Boolean mybatis = false;
    private Boolean mybatisPlus = false;
    private Boolean sessionRedis = false;
    private Boolean elasticsearch = false;
    private Boolean wx = false;
    private Boolean knife4j = false;
    private Boolean qcloud = false;
    private Boolean commonsLang3 = false;
    private Boolean easyexcel = false;
    private Boolean hutool = false;
    private Boolean test = false;

    private Map<String, DependencyConfigVO> dependencies;

    public Boolean getWeb() {
        return web;
    }

    public void setWeb(Boolean web) {
        this.web = web;
    }

    public Map<String, DependencyConfigVO> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Map<String, DependencyConfigVO> dependencies) {
        this.dependencies = dependencies;
    }

    public String get_groupId() {
        return _groupId;
    }

    public void set_groupId(String _groupId) {
        this._groupId = _groupId;
    }

    public String get_artifactId() {
        return _artifactId;
    }

    public void set_artifactId(String _artifactId) {
        this._artifactId = _artifactId;
    }

    public String get_version() {
        return _version;
    }

    public void set_version(String _version) {
        this._version = _version;
    }

    public String get_package() {
        return _package;
    }

    public void set_package(String _package) {
        this._package = _package;
    }

    public Boolean getMysql() {
        return mysql;
    }

    public void setMysql(Boolean mysql) {
        this.mysql = mysql;
    }

    public Boolean getRedis() {
        return redis;
    }

    public void setRedis(Boolean redis) {
        this.redis = redis;
    }

    public Boolean getFreemarker() {
        return freemarker;
    }

    public void setFreemarker(Boolean freemarker) {
        this.freemarker = freemarker;
    }

    public Boolean getDevtools() {
        return devtools;
    }

    public void setDevtools(Boolean devtools) {
        this.devtools = devtools;
    }

    public Boolean getConfigurationProcessor() {
        return configurationProcessor;
    }

    public void setConfigurationProcessor(Boolean configurationProcessor) {
        this.configurationProcessor = configurationProcessor;
    }

    public Boolean getAop() {
        return aop;
    }

    public void setAop(Boolean aop) {
        this.aop = aop;
    }

    public Boolean getMybatis() {
        return mybatis;
    }

    public void setMybatis(Boolean mybatis) {
        this.mybatis = mybatis;
    }

    public Boolean getMybatisPlus() {
        return mybatisPlus;
    }

    public void setMybatisPlus(Boolean mybatisPlus) {
        this.mybatisPlus = mybatisPlus;
    }

    public Boolean getSessionRedis() {
        return sessionRedis;
    }

    public void setSessionRedis(Boolean sessionRedis) {
        this.sessionRedis = sessionRedis;
    }

    public Boolean getElasticsearch() {
        return elasticsearch;
    }

    public void setElasticsearch(Boolean elasticsearch) {
        this.elasticsearch = elasticsearch;
    }

    public Boolean getWx() {
        return wx;
    }

    public void setWx(Boolean wx) {
        this.wx = wx;
    }

    public Boolean getKnife4j() {
        return knife4j;
    }

    public void setKnife4j(Boolean knife4j) {
        this.knife4j = knife4j;
    }

    public Boolean getQcloud() {
        return qcloud;
    }

    public void setQcloud(Boolean qcloud) {
        this.qcloud = qcloud;
    }

    public Boolean getCommonsLang3() {
        return commonsLang3;
    }

    public void setCommonsLang3(Boolean commonsLang3) {
        this.commonsLang3 = commonsLang3;
    }

    public Boolean getEasyexcel() {
        return easyexcel;
    }

    public void setEasyexcel(Boolean easyexcel) {
        this.easyexcel = easyexcel;
    }

    public Boolean getHutool() {
        return hutool;
    }

    public void setHutool(Boolean hutool) {
        this.hutool = hutool;
    }

    public Boolean getTest() {
        return test;
    }

    public void setTest(Boolean test) {
        this.test = test;
    }

}
