package com.codebuddy;

import com.codebuddy.controllers.Container;
import com.codebuddy.models.ManifestModel;

import java.util.ArrayList;

public class ContainerBeanList {
    public ArrayList<ContainerBean> getDataBeanList(String referenceNum) {
        ArrayList<ContainerBean> dataBeanList = new ArrayList<>();
        ManifestModel manifestModel = new ManifestModel();
        ArrayList<Container> list = manifestModel.getContainers(referenceNum);

        for (int i = 0; i < list.size(); i++) {
            dataBeanList.add(produce(list.get(i).getContainerNumber(), list.get(i).getSealNumber(),list.get(i).getSize(),
                    list.get(i).getStatus(),list.get(i).getPackages(),list.get(i).getCbm()));
        }

//        for (int i = 0; i< dataBeanList.size(); i++){
//            System.out.println(dataBeanList.get(i).getContainerNum());
//        }

        return dataBeanList;
    }

    /**
     * This method returns a DataBean object,
     * with name and country set in it.
     */
    private ContainerBean produce(String containerNum, String sealNum, String size, String status, String packages, String containerCBM) {
        ContainerBean dataBean = new ContainerBean();
        dataBean.setContainerNum(containerNum);
        dataBean.setSealNum(sealNum);
        dataBean.setSize(size);
        dataBean.setStatus(status);
        dataBean.setPackages(packages);
        dataBean.setContainerCBM(containerCBM);
        return dataBean;
    }
}
