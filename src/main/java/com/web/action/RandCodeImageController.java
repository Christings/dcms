package com.web.action;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.util.WebUtils;

/**
 * 验证码
 * 
 * @author qgl
 */
@Controller
public class RandCodeImageController {
	char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	private static final int count = 200;
	private static final int width = 100;
	private static final int height = 30;
	private static final int lineWidth = 2;

	@RequestMapping("/code")
	public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		// response.setContentType("image/png");

		// 在内存中创建图象
		final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		final Graphics2D graphics = (Graphics2D) image.getGraphics();

		// 设定背景颜色
		graphics.setColor(Color.WHITE); // ---1
		graphics.fillRect(0, 0, width, height);
		// 设定边框颜色
		// graphics.setColor(getRandColor(100, 200)); // ---2
		graphics.drawRect(0, 0, width - 1, height - 1);

		final Random random = new Random();
		// 随机产生干扰线，使图象中的认证码不易被其它程序探测到
		for (int i = 0; i < count; i++) {
			graphics.setColor(getRandColor(150, 200)); // ---3

			final int x = random.nextInt(width - lineWidth - 1) + 1; // 保证画在边框之内
			final int y = random.nextInt(height - lineWidth - 1) + 1;
			final int xl = random.nextInt(lineWidth);
			final int yl = random.nextInt(lineWidth);
			graphics.drawLine(x, y, x + xl, y + yl);
		}

		String randStr = "";
		// 取随机产生的认证码(4位数字)
		for (int i = 0; i < 4; i++) {
			final String resultCode = String.valueOf(codeSequence[random.nextInt(36)]);
			randStr += resultCode;
			// 将认证码显示到图象中,调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			// graphics.setColor(new Color(20 + random.nextInt(130), 20 + random
			// .nextInt(130), 20 + random.nextInt(130)));

			// 设置字体颜色
			graphics.setColor(Color.BLACK);
			// 设置字体样式
			// graphics.setFont(new Font("Arial Black", Font.ITALIC, 18));
			graphics.setFont(new Font("Times New Roman", Font.BOLD, 24));
			// 设置字符，字符间距，上边距
			graphics.drawString(resultCode, (23 * i) + 8, 23);
		}

		// 将认证码存入SESSION
		WebUtils.addCode(request, randStr);
		// 图象生效
		graphics.dispose();

		// 输出图象到页面
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}

	private Color getRandColor(int fc, int bc) { // 取得给定范围随机颜色
		final Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}

		final int r = fc + random.nextInt(bc - fc);
		final int g = fc + random.nextInt(bc - fc);
		final int b = fc + random.nextInt(bc - fc);

		return new Color(r, g, b);
	}
}