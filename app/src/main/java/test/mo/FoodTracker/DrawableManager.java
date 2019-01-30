package test.mo.FoodTracker;

public class DrawableManager {

    public static int getDrawable(String category) {
        switch (category) {
            case "Fruit & Veg":
                return R.drawable.veg;
            case "Meats":
                return R.drawable.meat;
            case "Dairy":
                return R.drawable.dairy;
            case "Grains":
                return R.drawable.grain;
            case "Oils":
                return R.drawable.oil;
            default:
                return R.drawable.other;
        }
    }
}
