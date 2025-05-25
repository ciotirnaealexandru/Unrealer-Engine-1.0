package org.engine.graphics;

import org.lwjgl.system.linux.Msghdr;

import java.util.Random;

public class Screen extends Render{

    private Render test;

    public Screen(int width, int height) {
        super(width, height);
        Random random = new Random();

        test = new Render(300, 300);
        for (int i = 0; i < 300 * 300; i++) {
            test.pixels[i] = random.nextInt();
        }
    }

    public void render() {
        // clear the screen
        for (int i = 0; i < width * height; i++)
            pixels[i] = 0;

        for (int i = 0; i < 80; i++) {
            int animx = (int) (Math.sin((System.currentTimeMillis() + i) % 1500.0 / 1500 * Math.PI * 2) * 200);
            int animy = (int) (Math.cos((System.currentTimeMillis() + i) % 1500.0 / 1500 * Math.PI * 2) * 200);

            draw(test, (width - 300) / 2 + animx, (height - 300) / 2 + animy);
        }
    }
}
