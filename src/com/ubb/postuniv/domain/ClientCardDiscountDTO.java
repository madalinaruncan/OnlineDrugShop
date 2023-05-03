package com.ubb.postuniv.domain;

public class ClientCardDiscountDTO {
    public ClientCard clientCard;
    public float discount;

    public ClientCardDiscountDTO(ClientCard clientCard, float discount) {
        this.clientCard = clientCard;
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "ClientCardDiscountDTO{" +
                "clientCard=" + clientCard +
                ", discount=" + discount +
                '}' + "\n";
    }
}
