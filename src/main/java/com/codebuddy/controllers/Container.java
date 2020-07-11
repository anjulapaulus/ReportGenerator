package com.codebuddy.controllers;

import com.codebuddy.ContainerBean;

public class Container {
    protected String containerNumber,sealNumber,size,status, packages,cbm;

    public Container(){}

    public Container(String containerNumber,String sealNumber, String size, String status, String packages, String cbm){
        this.containerNumber = containerNumber;
        this.sealNumber = sealNumber;
        this.size = size;
        this.status = status;
        this.packages = packages;
        this.cbm = cbm;
    }

    public void setContainerNumber(String containerNumber) {
        this.containerNumber = containerNumber;
    }

    public String getContainerNumber() {
        return containerNumber;
    }

    public void setSealNumber(String sealNumber) {
        this.sealNumber = sealNumber;
    }

    public String getSealNumber() {
        return sealNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getPackages() {
        return packages;
    }

    public void setCbm(String cbm) {
        this.cbm = cbm;
    }

    public String getCbm() {
        return cbm;
    }

    public Container produce(String containerNum, String sealNum, String size, String status, String packages, String containerCBM) {
        Container dataBean = new Container();
        dataBean.setContainerNumber(containerNum);
        dataBean.setSealNumber(sealNum);
        dataBean.setSize(size);
        dataBean.setStatus(status);
        dataBean.setPackages(packages);
        dataBean.setCbm(containerCBM);
        return dataBean;
    }
}