package org.tnmk.practicespringjpa.pro07multitenant.correctimplementation.datafactory;

import org.tnmk.practicespringjpa.pro07multitenant.correctimplementation.entity.WrongChildEntity;

public class WrongChildEntityFactory {

    public static WrongChildEntity constructChildEntity() {
        WrongChildEntity wrongChildEntity = new WrongChildEntity();
        wrongChildEntity.setName("Child_" + System.nanoTime());
        wrongChildEntity.setDescription("Description_" + System.nanoTime());
        return wrongChildEntity;
    }
}
