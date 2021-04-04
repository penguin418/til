public class Coffee {
    private Integer shot;
    private Integer water;
    private Integer syrup;
    private Boolean decaf;

    public Coffee(Integer shot, Integer water, Integer syrup, Boolean decaf){
        this.shot = shot;
        this.water = water;
        this.syrup = syrup;
        this.decaf = decaf;
    }

    public static class Builder{
        private Integer shot = 1;
        private Integer water = 7;
        private Integer syrup = 0;
        private Boolean decaf = false;

        public Builder shot(Integer shot) {
            this.shot = shot;
            return this;
        }

        public Builder water(Integer water){
            this.water = water;
            return this;
        }

        public Builder syrup(Integer syrup) {
            this.syrup = syrup;
            return this;
        }

        public Builder decaf(Boolean decaf) {
            this.decaf = decaf;
            return this;
        }

        public Coffee build(){
            return new Coffee(shot, water, syrup, decaf);
        }
    }
}
