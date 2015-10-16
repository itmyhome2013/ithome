package com.farm.core.file;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import javax.imageio.ImageIO;



import com.farm.core.time.TimeTool;
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片工具集合
 * 
 * @author
 * 
 */
public class ImageUtil {

	public final static int PHOTO_RATIO = 800; // 缩放图片系数
	static {
		System.setProperty("jmagick.systemclassloader", "no");
	}

	/**
	 * 删除一个图片文件
	 * 
	 * @param ImgStrName文件相对路径+文件名
	 * @return
	 */
	public final static boolean delete(String ImgStrName) {
		File file = new File(ImgStrName);
		return file.delete();
	}

	/**
	 * 计算一个可用目录出来
	 * 
	 * @return
	 */
	public final static String findSRC(String path) {
		String pathName = null;// 空闲目录
		File fileImgPath = new File(path);// 本地目录
		File[] fileS = fileImgPath.listFiles();
		// 是否已有空闲目录
		for (Iterator<File> iterator = Arrays.asList(fileS).iterator(); iterator
				.hasNext();) {
			File name = (File) iterator.next();
			if (name.isDirectory()) {
				if (name.listFiles().length < 800
						&& !name.getName().equals("temp")
						&& !name.getName().equals("Big")) {
					pathName = name.getName();
				}
			}
			if (pathName != null) {
				break;
			}
		}
		// 新建空闲目录
		if (pathName == null) {
			File newDirectory = new File(fileImgPath + "\\"
					+ TimeTool.getTimeDate14());
			if (newDirectory.mkdir()) {
				pathName = newDirectory.getName();
			}
		}
		return pathName;
	}

	/**
	 * 图片水印
	 * 
	 * @param pressImg
	 *            水印图片
	 * @param targetImg
	 *            目标图片
	 * @param x
	 *            修正值 默认在中间
	 * @param y
	 *            修正值 默认在中间
	 * @param alpha
	 *            透明度
	 */
	public final static void pressImage(String pressImg, String targetImg,
			int x, int y, float alpha) {
		try {
			File img = new File(targetImg);
			Image src = ImageIO.read(img);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			// 水印文件
			Image src_biao = ImageIO.read(new File(pressImg));
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			g.drawImage(src_biao, (wideth - wideth_biao) / 2,
					(height - height_biao) / 2, wideth_biao, height_biao, null);
			// 水印文件结束
			g.dispose();
			ImageIO.write((BufferedImage) image, "jpg", img);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 文字水印
	 * 
	 * @param pressText
	 *            水印文字
	 * @param targetImg
	 *            目标图片
	 * @param fontName
	 *            字体名称
	 * @param fontStyle
	 *            字体样式
	 * @param color
	 *            字体颜色
	 * @param fontSize
	 *            字体大小
	 * @param x
	 *            修正值
	 * @param y
	 *            修正值
	 * @param alpha
	 *            透明度
	 */
	public static void pressText(String pressText, String targetImg,
			String fontName, int fontStyle, Color color, int fontSize, int x,
			int y, float alpha) {
		try {
			File img = new File(targetImg);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			g.drawString(pressText, (width - (getLength(pressText) * fontSize))
					/ 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write((BufferedImage) image, "jpg", img);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 缩放 效果太差 ps: Graphics下的 还有 AffineTransform下的
	 * 缩放都是针对"图形"而不是"图像"的，所以处理后图片很不清晰
	 * 
	 * @param filePath
	 *            图片路径
	 * @param height
	 *            高度
	 * @param width
	 *            宽度
	 * @param bb
	 *            比例不对时是否需要补白
	 */
	public static void resizeImgcale(String filePath, int height, int width,
			boolean bb) {
		try {
			double ratio = 0.0; // 缩放比例
			File f = new File(filePath);
			BufferedImage bi = ImageIO.read(f);
			Image itemp = bi.getScaledInstance(width, height,
					BufferedImage.SCALE_SMOOTH);
			// 计算比例
			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
				if (bi.getHeight() > bi.getWidth()) {
					ratio = (new Integer(height)).doubleValue()
							/ bi.getHeight();
				} else {
					ratio = (new Integer(width)).doubleValue() / bi.getWidth();
				}
				AffineTransformOp op = new AffineTransformOp(AffineTransform
						.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(bi, null);
			}
			if (bb) {
				BufferedImage image = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null))
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				else
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				g.dispose();
				itemp = image;
			}
			ImageIO.write((BufferedImage) itemp, "jpg", f);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 计算字的长度
	 * 
	 * @param text
	 * @return
	 */
	public static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}

	/**
	 * 压缩图片
	 * 
	 * @param imgsrc
	 *            源文件
	 * @param imgdist
	 *            目标文件
	 * @param widthdist
	 *            宽
	 * @param heightdist
	 *            高
	 */
	public static void resizeImg(String imgsrc, String imgdist, int widthdist,
			int heightdist) {
		try {
			File srcfile = new File(imgsrc);
			if (!srcfile.exists()) {
				return;
			}
			Image src = javax.imageio.ImageIO.read(srcfile);
			BufferedImage tag = new BufferedImage(widthdist, heightdist,
					BufferedImage.TYPE_INT_RGB);
			/*
			 * SCALE_SMOOTH：尺寸平滑 SCALE_AREA_AVERAGING：尺度区平均 SCALE_FAST：尺度快速
			 * SCALE_REPLICATE：尺度复制
			 */
			tag.getGraphics().drawImage(
					src.getScaledInstance(widthdist, heightdist,
							Image.SCALE_FAST), 0, 0, null);
			FileOutputStream out = new FileOutputStream(imgdist);
			//JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			//encoder.encode(tag);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		 resizeImg("C:\\Documents and Settings\\Administrator\\桌面\\新建文件夹\\IMG_1224.jpg", "C:\\Documents and Settings\\Administrator\\桌面\\新建文件夹\\IMG_1225.jpg", 80, 60);
		// resize("d:/test_4.jpg", "d:/test_4_2.jpg", 800);
		
	}

}
