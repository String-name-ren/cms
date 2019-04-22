package com.waterelephant.common.utils;

public class DisablePhoneUtil {

	public static void main(String[] args) {
		System.out.println(DisablePhoneUtil.convert("13036120"));	;
	}
		
	public static String convert(Object object){
		
		if(!CommUtils.isNull(object)){
			
			String phone=String.valueOf(object);
			
			if(phone.length()==11){
				
				return 	phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
				
			}else if(phone.length()==8){
				
				return 	phone.replaceAll("(\\d{2})\\d{4}(\\d{2})","$1****$2");
			}
			
		}
				
		return "";		
	}
	
  
	public static String convertIdCard(Object object){
          
		if(!CommUtils.isNull(object)){
			
			String id_card=String.valueOf(object);
			return 	id_card.replaceAll("(\\d{6})\\d{8}([\\dXx]{4})","$1********$2");
				
		}
				
		return null;
		
	}
	
	
	public static String convertCardNo(Object object){
        
		if(!CommUtils.isNull(object)){
			String card_no=String.valueOf(object);
			if(card_no.length()==16){
			return 	card_no.replaceAll("(\\d{6})\\d{6}(\\d{4})","$1******$2");
			}else if(card_no.length()==19){
				return 	card_no.replaceAll("(\\d{6})\\d{9}(\\d{4})","$1******$2");
				}
		
		}
		return null;
		
	}
	
}
