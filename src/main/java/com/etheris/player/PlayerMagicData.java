package com.etheris.player;


public class PlayerMagicData {
    // save all player specific stats
    // PlayerMagicData Ether MaxEther Purity Manipulation

    
    private float ether;
    private float maxEther;
    private float purity;
    private float manipulation;

    public PlayerMagicData(float ether, float maxEther, float purity, float manipulation) {
        this.ether = ether;
        this.maxEther = maxEther;
        this.purity = purity;
        this.manipulation = manipulation;
    }
    public float getEther() {
        return ether;
    }
    public void setEther(float ether) {
        this.ether = Math.min(ether, maxEther);
    }

    public float modifyEther(float amount) {
        this.ether += amount;
        return this.ether;
    }

    public float forceConsumeEther(float amount) {
        this.ether -= amount;
        return this.ether;
    }
    public float getMaxEther() {
        return maxEther;
    }
    public void setMaxEther(float maxEther) {
        this.maxEther = Math.max(0, maxEther);
        if (this.ether > this.maxEther) {
            this.ether = this.maxEther;
        }
    }
    public float getPurity() {
        return purity;

    }
    public void setPurity(float purity) {
        this.purity = Math.max(0, Math.min(purity, 100));
    }
    public float getManipulation() {
        return manipulation;
    }
    public void setManipulation(float manipulation) {
        this.manipulation = manipulation;
    }
    public boolean consumeEther(float amount) {
        if (this.ether >= amount) {
            this.ether -= amount;
            return true;
            }
            return false;
        }
        public void regenerateEther(float amount) {
            setEther(this.ether + amount);
        }
    }    

