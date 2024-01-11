import java.util.Random;

public abstract class BattleLoc extends Location {
    private Obstacle obstacle;
    private String award;
    private int maxObstacle;

    public BattleLoc(Player player, String name, Obstacle obstacle, String award, int maxObstacle) {
        super(player, name);
        this.obstacle = obstacle;
        this.award = award;
        this.maxObstacle = maxObstacle;

    }

    @Override
    public boolean onLocation() {
        int obstNumber = this.randomObstacleNumber();
        System.out.println("Şuan buradasınız: " + this.getName());
        System.out.println("Dikkatli Ol! Burada " + obstNumber + " tane " + this.getObstacle().getName() + " yaşıyor.");
        System.out.print("<S>avaş veya <K>aç? ");
        String selectCase = input.nextLine().toUpperCase();
        if (selectCase.equals("S") && combat(obstNumber)) {
                System.out.println(this.getName() + " tüm düşmanları yendiniz.");
                return true;
            }

        if (this.getPlayer().getHealth() <= 0) {
            System.out.println("Öldünüz!");
            return false;
        }
        return true;
    }

    public boolean combat(int obsNumber) {
        for (int i = 1; i <= obsNumber; i++) {
            this.getObstacle().setHealth(this.getObstacle().getOriginalHealth());
            playerStats();
            obstacleStats(i);
            while (this.getPlayer().getHealth() > 0 && this.getObstacle().getHealth() > 0) {
                System.out.print("<V>ur veya <K>aç? ");
                String selectCombat = input.nextLine().toUpperCase();
                if (selectCombat.equals("V")) {

                    // isStart() içerisinde tanımlı olan %50 ihtimalle başlama opsiyonu geliştirdik. Buradaki kodu
                    // toplamak için playerHit() ve obstacleHit() fonksiyonları yazarak daha temiz bir görünüm elde ettim.

                    if (isStart()) {
                        playerHit();

                        // Canavar eğer 0 altına düşerse bir daha vurmaması için kontrol ekledim.

                        if(this.getObstacle().getHealth() > 0){

                        obstacleHit();}
                    }
                    else if (!isStart() && this.getObstacle().getHealth() > 0){

                        obstacleHit();
                        playerHit();

                    }
                } else {
                    return false;
                }
            }
            if (this.getObstacle().getHealth() < this.getPlayer().getHealth()) {
                System.out.println("Düşmanı yendiniz!");
                if (this.getObstacle().getId() != 4) {
                    System.out.println(this.getObstacle().getAward() + " para kazandınız!");
                    this.getPlayer().setMoney((this.getPlayer().getMoney() + this.getObstacle().getAward()));
                    System.out.println("Güncel paranız: " + this.getPlayer().getMoney());
                    String awardType = this.getName();
                    switch (awardType) {

                        case "Cave":
                            this.getPlayer().getInventory().setFood(1);
                            break;
                        case "Forest":
                            this.getPlayer().getInventory().setWood(1);
                            break;
                        case "River":
                            this.getPlayer().getInventory().setWater(1);
                            break;
                    }
                } else if (this.getObstacle().getId() == 4) {
                    randomAward();
                }
            }
            else {
                return false;
            }
        }

        return true;
    }



    public void afterHit() {
        System.out.println("Canınız: "  + this.getPlayer().getHealth());
        System.out.println(this.getObstacle().getName() + " Canı: " + this.getObstacle().getHealth());
        System.out.println();
    }
    public void playerStats(){
        System.out.println("Oyuncu Değerleri");
        System.out.println("-------------------------");
        System.out.println("Sağlık: " + this.getPlayer().getHealth());
        System.out.println("Silah: " + this.getPlayer().getInventory().getWeapon().getName());
        System.out.println("Zırh: " + this.getPlayer().getInventory().getArmor().getName());
        System.out.println("Bloklama: " + this.getPlayer().getInventory().getArmor().getBlock());
        System.out.println("Hasar: " + this.getPlayer().getTotalDamage());
        System.out.println("Para: " + this.getPlayer().getMoney());
        System.out.println("-------------------------");

    }
    public void obstacleStats(int i){
        System.out.println(i + "." + this.getObstacle().getName() + " Değerleri");
        System.out.println("-------------------------");
        System.out.println("Sağlık: " + this.getObstacle().getHealth());

        if (this.getObstacle().getId() != 4) {
            System.out.println("Hasar: " + this.getObstacle().getDamage());
            System.out.println("Ödül: " + this.getObstacle().getAward());
        }
        else if (this.getObstacle().getId() == 4) {
            System.out.println("Hasar: 3 - 6");
            System.out.println("Ödül: <Rastgele: Para, Silah veya Zırh>");

        }

        System.out.println("-------------------------");
    }
    public int randomObstacleNumber(){
        Random r = new Random();
        return r.nextInt(3) + 1;
    }
    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public boolean isStart() {
        return Math.random() > 0.5;}

    public void playerHit() {
        System.out.println("Siz vurdunuz! ");
        this.getObstacle().setHealth(this.getObstacle().getHealth() - this.getPlayer().getTotalDamage());
        afterHit();
    }

    public void obstacleHit() {
        System.out.println("Canavar Size Vurdu!");
        int obstacleDamage = this.getObstacle().getDamage() - this.getPlayer().getInventory().getArmor().getBlock();
        if (obstacleDamage < 0) {
            obstacleDamage = 0;
        }

        if (this.getObstacle().getId() == 4) {
            int snakeRandomizer = this.getObstacle().randomNumber();
            obstacleDamage += snakeRandomizer;
        }
        this.getPlayer().setHealth(this.getPlayer().getHealth() - obstacleDamage);
        afterHit();
    }

    public void randomAward(){
        // Master kategorileri yazdım

        double winWeapon = 0.15;
        double winArmor = 0.15;
        double winMoney = 0.25;

        // Sub kategorileri tanımladım

        double winRifle = winWeapon * 0.20;
        double winSword = winWeapon * 0.30;
        double winGun = winWeapon * 0.5;

        double winHeavyArmor = winArmor * 0.20;
        double winMidArmor = winArmor * 0.30;
        double winLowArmor = winArmor * 0.5;

        double win10Money = winMoney * 0.2;
        double win5Money = winMoney * 0.30;
        double win1Money = winMoney * 0.50;

        // Bütün kazanma şanslarını bir listeye atıp ordan rastgele %55 oranla çekeceğiz.
        double[] subCategoryProbabilities = {winRifle, winSword, winGun, winHeavyArmor, winMidArmor, winLowArmor, win10Money, win5Money, win1Money};
        String selectedSubCategory = selectSubCategory(subCategoryProbabilities);
        switch (selectedSubCategory){
            case "Tüfek":
                this.getPlayer().getInventory().setWeapon(Weapon.getWaeaponObjByID(3));
                break;
            case "Silah":
                this.getPlayer().getInventory().setWeapon(Weapon.getWaeaponObjByID(2));
                break;
            case "Tabanca":
                this.getPlayer().getInventory().setWeapon(Weapon.getWaeaponObjByID(1));
                break;
            case "Ağır Zırh":
                this.getPlayer().getInventory().setArmor(Armor.getArmorObjByID(3));
                break;
            case "Orta Zırh":
                this.getPlayer().getInventory().setArmor(Armor.getArmorObjByID(2));
                break;
            case"Hafif Zırh":
                this.getPlayer().getInventory().setArmor(Armor.getArmorObjByID(1));
            case "10 Para":
                this.getPlayer().setMoney(this.getPlayer().getMoney() + 10);
                break;
            case "5 Para":
                this.getPlayer().setMoney(this.getPlayer().getMoney() + 5);
                break;
            case "1 Para":
                this.getPlayer().setMoney(this.getPlayer().getMoney() + 1);
                break;
        }
        System.out.println(selectedSubCategory + " kazandınız!");
    }
    private String selectSubCategory(double[] probabilities) {
        Random random = new Random();
        double randomValue = random.nextDouble();

        double cumulativeProbability = 0.0;
        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];
            if (randomValue < cumulativeProbability) {
                switch (i) {
                    case 0:
                        return "Tüfek";
                    case 1:
                        return "Silah";
                    case 2:
                        return "Tabanca";
                    case 3:
                        return "Ağır Zırh";
                    case 4:
                        return "Orta Zırh";
                    case 5:
                        return "Hafif Zırh";
                    case 6:
                        return "10 Para";
                    case 7:
                        return "5 Para";
                    case 8:
                        return "1 Para";
                }
            }
        }
        return "Maalesef bu sefer hiç bir şey kazanamadınız!";
    }

}
