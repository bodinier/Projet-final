package com.excilys.librarymanager.model;

import com.excilys.librarymanager.exception.DaoException;

public enum Abonnement {
    BASIC, PREMIUM, VIP; 

    /**
     * 
     * @param aboString
     * @return the enum type linked with the string in param
     * @throws Exception
     */
    public static Abonnement aboFromString(String aboString) throws DaoException {
        Abonnement abo=null;
        try {
            aboString.toLowerCase();
                    switch (aboString){
                        case "basic" :
                            abo = Abonnement.BASIC;
                            break;
                        case "premium" :
                            abo = Abonnement.PREMIUM;
                            break;
                        case "vip" :
                            abo = Abonnement.VIP;
                            break;
                    }
        } catch (Exception e) {
            throw new DaoException("Impossible to cast the string to Abonnement type", e); 
            }
        return abo;
    }

    public static final void main(String args[]){
        Abonnement abo = Abonnement.BASIC;
        System.out.println(abo.toString());
    }
}