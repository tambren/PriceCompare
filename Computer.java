
public class Computer {
    
    String brand;
    String name;
    String cpu;
    String ram;
    String monitor;
    String resolution;
    String price;
     
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public String getRam() {
        return ram;
    }
    public void setRam(String ram) {
        this.ram = ram;
    }
   
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCpu() {
        return cpu;
    }
    public void setCpu(String cpu) {
        this.cpu = cpu;
    }
    public String getMonitor() {
        return monitor;
    }
    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }
    public String getResolution() {
        return resolution;
    }
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
    public String getPrice() {
        return price;
    }
    
    public int getIntPrice() {
        return Integer.parseInt(this.price);
    }
    public void setPrice(String price) {
        this.price = price;
    }
    
    @Override
    public String toString() {
        return "Computer [name=" + name + ", cpu=" + cpu + ", ram=" + ram + ", monitor=" + monitor + ", resolution="
                + resolution + ", price=" + price + "]";
    }
   
}
