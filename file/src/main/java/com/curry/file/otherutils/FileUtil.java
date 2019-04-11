package com.curry.file.otherutils;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

/**
 * 文件工具类--ldz
 */
public class FileUtil {
    private static final int BUFFER_SIZE = 16 * 1024;
    private static String savePath;
    private static String imagePathBase;

    //	static
//	{
//		savePath = Constants.SAVE_PATH;
//	}
//
    public static void fileCopy(File src, File dest) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(dest), BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

//    /***
//     * 上传本地文件
//     *
//     * @param filepath
//     * @param fileName
//     * @return
//     * @throws Exception
//     */
//    public static String copyFile(File[] filepath, String[] fileName) throws Exception {
//        if (filepath == null || fileName == null)
//            return null;
//        String imageUrls = null;
//        Random random = new Random();
//        String uploadPathAndName = getSavePath() + System.currentTimeMillis() + random.nextInt(10000)
//                + fileName[0];
//
//			/* sturts的临时文件拷贝到本地 */
//        fileCopy(filepath[0], new File(uploadPathAndName));
//        imageUrls = uploadPathAndName;
//        return imageUrls;
//    }

    /**
     * 获得文件大小(字节)
     *
     * @param filePath
     * @return
     * @author zhaochunjiao
     * @create 2016年2月23日 上午11:16:44
     */
    public static long getFileSizeByte(String filePath) {
        FileChannel fc = null;
        long fileSize = 0;
        try {
            File f = new File(filePath);
            if (f.exists() && f.isFile()) {
                FileInputStream fis = new FileInputStream(f);
                fc = fis.getChannel();
                fileSize = fc.size();
                fis.close();
            } else {
                Log.e("myLog", "file doesn't exist or is not a file");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fc) {
                try {
                    fc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return fileSize;
    }

    /**
     * 文件大小转换成可显示的Mb,Gb和kb方法
     *
     * @param size
     * @return
     * @author zhaochunjiao
     * @create 2016年2月23日 上午11:18:34
     */
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

//	/**
//	 * 获得视频文件的时长(毫秒)
//	 * @param fileName
//	 * @return
//	 * @author zhaochunjiao
//	 * @create 2016年4月26日 下午6:11:25
//	 */
//	public static long takeVideoTime(String fileName) {
////		File source = new File("F:\\[电影天堂www.dygod.cn]碟中谍3BD.rmvb");
//		File source = new File(fileName);
//        Encoder encoder = new Encoder();
//        long ls = 0;
//        try {
//             MultimediaInfo m = encoder.getInfo(source);
//             ls = m.getDuration();
//             return ls;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ls;
//    }
//
//
//	/**
//	 * 获得文件的ContentType
//	 * @param pathToFile
//	 * @return
//	 * @author zhaochunjiao
//	 * @create 2016年4月26日 下午6:11:02
//	 */
//	public static String printContentType(String pathToFile) {
//        Path path = Paths.get(pathToFile);
//        String contentType = null;
//        try {
//            contentType = Files.probeContentType(path);
//            return contentType;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //System.out.println("File content type is : " + contentType);
//        return contentType;
//    }
//
//	/**
//	 * 将输入转换成文件
//	 * @param ins
//	 * @param path
//	 */
//	public static void writeToFile(InputStream ins, String path) {
//		try {
//			OutputStream out = new FileOutputStream(new File(path));
//			int read = 0;
//			byte[] bytes = new byte[1024];
//
//			while ((read = ins.read(bytes)) != -1) {
//				out.write(bytes, 0, read);
//			}
//			out.flush();
//			out.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

//	/*
//	 * 
//	 * 使用例子：【 String[] productpics = UploadImagesUtil.uploadImages(productpicture, productpictureFileName, creatorId,
//	 * ACT.product.getModelId(), ACT.product.getDealEntityType(), ACT.product.getModelId(), 440, 183, false, imageManager);】
//	 * 文件的上传 执行图片文件的上传功能，上传过程如下： 1）新建Image对象
//	 * 2）调用imageManager.addImage()方法，通过该方法，将产生图片的url 具体的细节在代码的注释中 参数1：文件 参数2：文件名
//	 * 3)isWatermark == ture ;表示打水印
//	 */
//	public static String[] uploadImages(File[] images, String[] imagesFileName,long creatorId ,short refEntityId, short refEntityType,
//			short refModule, int height ,int thumbnailsHeight ,boolean isWatermark, ImageManager imageManager) throws Exception {
//		if (images == null || imagesFileName == null)
//			return null;
//		String[] imageUrls = new String[images.length];
//		/* 新建图片对象，并调用imageManager的addImage()方法 */
//		for (int i = 0; i < images.length; i++) {
//			if (images[i] == null) {
//				imageUrls[i] = "";
//			}else {
//				/* 图片库功能，管理图片的上传 */
//				Image image = new Image(); // 新建Image对象
//
//				/* 设置Image对象的属性，属性主要为页面传递。由于目前为例子，所以属性设置为固定值 */
//				image.setHeight(height);
//				image.setThumbnailsHeight(thumbnailsHeight);
//
//				// 图片的创建者信息
//				image.setCreatorId(creatorId); // 创建者Id
//				image.setRefEntityId(refEntityId);
//				image.setRefEntityType(refEntityType);
//				image.setRefModule(refModule);
//				image.setCreatorType(Image.IMAGE_CREATORTYPE_OPERATOR);
//				/* uploadPathAndName为目标文件路径,即将图片上传到本地应用的某个文件夹内，名字为上传时所用的名字 */
//				// String uploadPathAndName = getSavePath() + imagesFileName[i];
//				Random random = new Random();
//				String uploadPathAndName = getSavePath()
//						+ System.currentTimeMillis() + i + random.nextInt(10000)
//						+ imagesFileName[i];
//				/* sturts的临时文件拷贝到本地 */
//				fileCopy(images[i], new File(uploadPathAndName));
//
//				/*
//				 * 调用图片库imageManager的addImage方法，上传图片并生成图片url
//				 * addImage需2个参数：第一个为上面生成的Image对象，第二个参数为本地图片的路径
//				 */
//				String imgUrl = imageManager.addImage(image, uploadPathAndName);
//				imageUrls[i] = imgUrl;
//				
//				/*给图片打水印*/
//				if(isWatermark){
//					imageManager.watermarkImage(imgUrl);
//				}
//			}
//		}
//		return imageUrls;
//	}

//	/***
//	 * 把关联图片copy到本地制定目录
//	 * 
//	 * @param images
//	 * @param imagesFileName
//	 * @return
//	 * @throws Exception
//	 */
//	@SuppressWarnings("unused")
//	private static String[] copyImages(File[] images, String[] imagesFileName)
//			throws Exception {
//		if (images == null || imagesFileName == null)
//			return null;
//		String[] imageUrls = new String[imagesFileName.length];
//		/* 新建图片对象，并调用imageManager的addImage()方法 */
//		for (int i = 0; i < imagesFileName.length; i++) {
//			/* 图片库功能，管理图片的上传 */
//			Image image = new Image(); // 新建Image对象
//
//			Random random = new Random();
//			/* uploadPathAndName为目标文件路径,即将图片上传到本地应用的某个文件夹内，名字为上传时所用的名字 */
//			String uploadPathAndName = getSavePath()
//					+ System.currentTimeMillis() + i + random.nextInt(10000)
//					+ imagesFileName[i];
//
//			/* sturts的临时文件拷贝到本地 */
//			fileCopy(images[i], new File(uploadPathAndName));
//			imageUrls[i] = uploadPathAndName;
//		}
//		return imageUrls;
//	}

    //	/***
//	 * 
//	 * @param viewContent
//	 * @param title
//	 * @param localURLall == getRequest().getSession().getServletContext().getRealPath("/")
//	 * @param creatorId
//	 * @param refEntityId
//	 * @param refEntityType
//	 * @param refModule
//	 * @param height
//	 * @param thumbnailsHeight
//	 * @param isWatermark == ture ;表示打水印
//	 * @return
//	 * @throws Exception
//	 */
//	public static String loadImg(String viewContent, String localURLall, long creatorId ,short refEntityId, short refEntityType,
//			short refModule, int height ,int thumbnailsHeight ,boolean isWatermark, ImageManager imageManager) throws Exception {
//		String url;
//		boolean flage = false;
//		String postfix = null;
//		String uploadPathAndName = savePath;
//		String regEx_span1 = "<[\\s]*?span[^>]*?>";
//		String regEx_span2 = "<[\\s]*?\\/[\\s]*?span[\\s]*?>";
//		String str_span = viewContent.replaceAll(regEx_span1, "").replaceAll(
//				regEx_span2, "");
//		Pattern p = Pattern.compile("<img");
//		String[] str = p.split(str_span);
//		for (int j = 1; j < str.length; j++) {
//			/* 主要针对QQ的反盗链 */
//			Pattern p1 = Pattern.compile("src=");
//			String[] src = p1.split(str[j]);
//			char rex = '"';
//			Pattern p2 = Pattern.compile(String.valueOf(rex));
//			String[] imgsrc = p2.split(src[1]);
//			url = imgsrc[1];
//			String imagePath = "";
//
//			/* 判断是否来自本网站 */
//
//			Pattern p3 = Pattern.compile(imagePathBase);
//			String[] islocalhost = p3.split(url);
//			if (islocalhost.length == 1) {
//				postfix = url.substring(url.lastIndexOf(".") + 1, url.length())
//						.toLowerCase();
//				// 可下载的图片类型gif|png|jpg|jpeg|bmp
//				if (postfix.equals("jpg") || postfix.equals("jpeg")
//						|| postfix.equals("gif") || postfix.equals("png")
//						|| postfix.equals("bmp")) {
//					
////					Pattern p4 = Pattern.compile("backend");
////					String[] localURLs = p4.split(localURLall);
////					String localURL = localURLs[0];
//					String replaceurl = url.substring(1, url.length());
//					replaceurl = replaceurl.replace("%20", " ");
//					String newurl = "/var/www/html/backend/" + replaceurl;
//					Random random = new Random();
//					String filename = uploadPathAndName
//							+ System.currentTimeMillis() + j
//							+ random.nextInt(10000) + ".jpg";
//					try {
//						// 建立相关的字节输入流
//						FileInputStream fr = new FileInputStream(newurl);
//
//						FileOutputStream fw = new FileOutputStream(filename);
//						byte buffer[] = new byte[1]; // 声明一个byte型的数组，数组的大小是512个字节
//						while (fr.read(buffer) != -1) {
//							fw.write(buffer);
//						}
//						fw.close();
//						fr.close();
//
//						// 上传图片
//						Image image = new Image();
//						image.setHeight(height);
//						image.setThumbnailsHeight(thumbnailsHeight);
//						image.setCreatorId(creatorId); // 创建者Id
//						image.setRefEntityId(refEntityId);
//						image.setRefEntityType(refEntityType);
//						image.setRefModule(refModule);
//						image.setCreatorType(Image.IMAGE_CREATORTYPE_OPERATOR);
//						imagePath = imageManager.addImage(image, filename);
//						/*给图片打水印*/
//						if(isWatermark){
//							imageManager.watermarkImage(imagePath);
//						}
//						flage = false;
//					} catch (Exception e) {
//						flage = true;
//					}
//				}
//				/* 替换原有地址 */
//				if (flage == false) {
//					String oldChar = url;
//					String newChar = imagePathBase + imagePath;
//					viewContent = str_span.replace(oldChar, newChar);
//				}
//			}
//			str_span = viewContent;
//		}
//
//		return viewContent;
//	}	
    public static String getSavePath() {
        return savePath;
    }

    public static void setSavePath(String savePath) {
        FileUtil.savePath = savePath;
    }

    public static String getImagePathBase() {
        return imagePathBase;
    }

    public static void setImagePathBase(String imagePathBase) {
        FileUtil.imagePathBase = imagePathBase;
    }
}
