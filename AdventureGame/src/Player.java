import java.util.Scanner;
public class Player {
    private int health;
    private int originalHealth;
    private int damage;
    private int money;
    private String name;
    private String charName;
    private Scanner inp = new Scanner(System.in);
    private Inventory inventory;

    public Player(String name){

        this.name = name;
        this.inventory = new Inventory();
    }

    public int getOriginalHealth() {
        return originalHealth;
    }

    public void setOriginalHealth(int originalHealth) {
        this.originalHealth = originalHealth;
    }

    public void selectChar() {
        GameChar[] charList = {new Samurai(), new Archer(), new Knight()};
        System.out.println("Karakterler");
        System.out.println("-----------------------------------");
        for(GameChar gameChar : charList) {
            System.out.println("ID: "+ gameChar.getId()+ "\t Karakter: " + gameChar.getName() + "\t Hasar: " + gameChar.getDamage()
            + "\t Sağlık: " + gameChar.getHealth() + "\t Para: " + gameChar.getMoney());
        }
        System.out.println("-----------------------------------");
        System.out.print("Lütfen oyuna başlamak için bir karakter seçiniz: ");
        int selectChar = inp.nextInt();
        switch (selectChar) {
            case 1:
                initPlayer(new Samurai());
                break;
            case 2:
                initPlayer(new Archer());
                break;
            case 3:
                initPlayer(new Knight());
                break;
            default:
                initPlayer(new Samurai());
        }
    }

    public void initPlayer(GameChar gameChar) {
        this.setDamage(gameChar.getDamage());
        this.setHealth(gameChar.getHealth());
        this.setMoney(gameChar.getMoney());
        this.setCharName(gameChar.getName());
        this.setOriginalHealth(gameChar.getHealth());
    }

    public void printInfo(){
        System.out.println("Karakter: " + this.getCharName() +
                ", Silah: " + this.getInventory().getWeapon().getName() +
                ", Zırh: " + this.getInventory().getArmor().getName() +
                ", Bloklama: " + this.getInventory().getArmor().getBlock() +
                ", Hasar: " + this.getTotalDamage() + ", Sağlık: " + this.getHealth() +
                ", Para: " + getMoney() +
                ", Yemek: " + this.getInventory().getFood() +
                ", Odun: " + this.getInventory().getWood() +
                ", Su: " + this.getInventory().getWater());
    }
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getTotalDamage() {return damage + getInventory().getWeapon().getDamage();}
    public int getDamage() {
        return damage ;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public  Weapon getWeapon() {
        return this.getInventory().getWeapon();
    }


}
