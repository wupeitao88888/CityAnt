package com.cityant.main.utlis;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;

import com.cityant.main.R;


public class ExpressionUtil {
	/**
	 * ��spanableString���������жϣ������Ҫ�����Ա���ͼƬ����
	 * 
	 */
	public static void dealExpression(Context context,
			SpannableString spannableString, Pattern patten, int start)
			throws SecurityException, NoSuchFieldException,
			NumberFormatException, IllegalArgumentException,
			IllegalAccessException {
		Matcher matcher = patten.matcher(spannableString);
		while (matcher.find()) {
			String key = matcher.group();
			if (matcher.start() < start) {
				continue;
			}
			Field field = R.drawable.class.getDeclaredField(key);

			int resId = Integer.parseInt(field.get(null).toString()); // ͨ������ƥ��õ����ַ������ͼƬ��Դid

			if (resId != 0) {
				Bitmap bitmap = BitmapFactory.decodeResource(
						context.getResources(), resId);
				ImageSpan imageSpan = new ImageSpan(bitmap); // ͨ��ͼƬ��Դid���õ�bitmap����һ��ImageSpan����װ
				int end = matcher.start() + key.length(); // �����ͼƬ���ֵĳ��ȣ�Ҳ����Ҫ�滻���ַ�ĳ���
				spannableString.setSpan(imageSpan, matcher.start(), end,
						Spannable.SPAN_INCLUSIVE_EXCLUSIVE); // ����ͼƬ�滻�ַ��й涨��λ����
				if (end < spannableString.length()) { // �������ַ�δ��֤�꣬�����
					dealExpression(context, spannableString, patten, end);
				}
				break;
			}
		}
	}

	/**
	 * �õ�һ��SpanableString����ͨ������ַ�,�����������ж�
	 * 
	 */
	public static SpannableString getExpressionString(Context context,
			String str, String zhengze) {
		SpannableString spannableString = new SpannableString(str);
		Pattern sinaPatten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE); // ͨ�����������ʽ�����һ��pattern
		try {
			dealExpression(context, spannableString, sinaPatten, 0);
		} catch (Exception e) {
			Log.e("dealExpression", e.getMessage());
		}
		return spannableString;
	}
}