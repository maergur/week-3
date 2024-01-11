import java.util.Scanner;
public class Game {
    private Scanner inp = new Scanner(System.in);
    public void start ()  {

        System.out.println("Macera Oyunu'na Hoşgeldiniz!");
        System.out.print("Lütfen bir isim giriniz: ");
        String playerName = inp.nextLine();
        Player player = new Player(playerName);
        System.out.println("Sayın " + player.getName() + " bu karanlık ve sisli adaya hoşgeldiniz!" +
                " Burada yaşananların hepsi gerçek...");
        player.selectChar();

        Location location = null;
        while(true){
            player.printInfo();
            System.out.println();
            System.out.println("-----------Bölgeler-----------");
            System.out.println();
            System.out.println("1 - Güvenli Ev --> Burası sizin için güvenli bir ev, düşman yoktur!");
            System.out.println("2 - Mağaza --> Silah veya Zırh satın alabilirsiniz");
            System.out.println("3 - Mağara --> Ödül: <Yemek>, dikkatli ol karşına zombiler çıkabilir.");
            System.out.println("4 - Orman --> Ödül: <Odun>, dikkatli ol karşına vampirler çıkabilir.");
            System.out.println("5 - Nehir --> Ödül: <Su>, dikkatli ol karşına ayılar çıkabilir.");
            System.out.println("6 - Maden --> Ödül: <Rastgele: Para, Silah veya Zırh>, dikkatli ol karşına yılanlar çıkabilir.");
            System.out.println("0 - Oyunu sonlandır.");
            System.out.println();
            System.out.print("Lütfen gitmek istediğiniz bölgeyi seçiniz: ");
            int selectLoc = inp.nextInt();

        switch (selectLoc){
            case 0:
                location = null;
                break;
            case 1:
                location = new SafeHouse(player);
                break;
            case 2:
                location = new ToolStore(player);
                break;
            case 3:
                if (player.getInventory().getFood() == 0)
                { location = new Cave(player);}
                else if (player.getInventory().getFood() == 1) {
                    { System.out.println("Bu bölgeyi tamamlandınız! Lütfen başka bir bölge seçiniz!");
                        location = new SafeHouse(player);}
                }
                break;
            case 4:
                if (player.getInventory().getWood() == 0)
                { location = new Forest(player);}
                else if (player.getInventory().getWood() == 1) {
                    { System.out.println("Bu bölgeyi tamamlandınız! Lütfen başka bir bölge seçiniz!");
                        location = new SafeHouse(player);}
                }
                break;
            case 5:
                if (player.getInventory().getWater() == 0)
                { location = new River(player);}
                else if (player.getInventory().getWater() == 1) {
                    { System.out.println("Bu bölgeyi tamamlandınız! Lütfen başka bir bölge seçiniz!");
                        location = new SafeHouse(player);}
                }
                break;
            case 6:
                location = new Mines(player);
                break;
            default:
                location = new SafeHouse(player);
        }

        if (location == null) {

            System.out.println("Bu maceradan çok kolay vazgeçtin...");
            break;
        }

        if(!location.onLocation()) {
            System.out.println("Game Over.");
            break;
        }

        // Eğer Güvenli Ev'e dönüyorsak ve tüm toplanması gereken malzemeleri topladıysak oyunu kazanıyoruz.

        if(location.getName() == "Güvenli Ev" && player.getInventory().getFood() == 1 &&
                (player.getInventory().getWater() == 1) &&
                (player.getInventory().getWood()) == 1) {
                System.out.println("Tebrikler! Oyunu Kazandınız!");
            break;
        }

        }
    }
}
