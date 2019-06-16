package fr.jcorioland.hellojava;

import java.net.InetAddress;
import org.springframework.web.bind.annotation.*;
//import java.applet.Applet;
//import java.awt.Color;
//import java.awt.Graphics;


@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        String hostname = "Unknown";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            // nothing.
        }
//        return "<html><body><H1><b><font color=green>Hello WSIB DevOps 2018 !!!!! I am running on Kubernetes pod:  " + hostname +"</font></b></H1></body></html>";
        return "<html><body><img src='https://www.stratoscale.com/wp-content/uploads/Kubernetes-logo.png'><H1><b><font color=green>WSIB Digital Factory DevOps 2018 !!!!! I am running on K8s Pods:  " + hostname +"</font></b></H1></body></html>";
    }

    @RequestMapping("/echo")
    public String test() {
        return "echo";
    }
}

/*public class Basic extends Applet{
    public void paint(Graphic g)
    {
        g.setColor(Color.red);
        g.drawString("Hello World!", 50, 100);
    }
}
*/
