package com.gaorui.Service;




import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gaorui.ISpring.ISpring;
import com.gaorui.dao.UserDao;
import com.gaorui.entity.Carpooling;
import com.gaorui.entity.Carpooling_user;
import com.gaorui.entity.Integral;
import com.gaorui.entity.User;
import com.gaorui.redisdao.redisdao;
import com.gaorui.util.CommonUtil;

public class SpringManager implements ISpring{
	@Resource
	private UserDao userDao;
	@Resource
	private redisdao redisdao1;
	@Override
	public User LoginCl(Long user_tel,String password) {
		
		return userDao.LoginCl(user_tel, password);
	}
	@Override
	public Integral ShowPersonalInfo(Long user_id) {
		
		return userDao.ShowPersonalInfo(user_id);
	}
	
	//需要计算出用户周围的所有的距离范围
	@Override
	public  List<Carpooling> ShowS_carPooling(double user_longitude,
			double user_latitude, int distanceAround) {
		double Big1_user_longitude = 0;
		double Big1_user_latitude = 0;
		double Big2_user_longitude = 0;
		double Big2_user_latitude = 0;
		return userDao.ShowS_carPooling(Big1_user_longitude, Big1_user_latitude, Big2_user_longitude, Big2_user_latitude);
	}
	@Override
	public Carpooling SelectCarpoolingByCarpooling_id(Long Carpooling_id) {
		
		return userDao.SelectCarpoolingByCarpooling_id(Carpooling_id);
	}
	@Override
	public User SelectUserByUser_id(Long user_id) {
		
		return userDao.SelectUserByUser_id(user_id);
	}
	@Override
	public int JoinCarpooling(Long Carpooling_id, Long user_id) {
	
		return userDao.JoinCarpooling(Carpooling_id, user_id) ;
	}
	@Override
	public Carpooling_user SelectCarpooling_idByUser_id(Long user_id) {
		
		return userDao.SelectCarpooling_idByUser_id(user_id);
	}
	@Override
	public List<Carpooling_user> SelectUser_idByCarpooling_id(
			Long Carpooling_id, Long user_id) {
		
		return userDao.SelectUser_idByCarpooling_id(Carpooling_id, user_id);
	}
	@Override
	public int updatecu_statusByUser_idCarpooling_id(Long user_id,
			Long Carpooling_id) {
		
		return userDao.updatecu_statusByUser_idCarpooling_id(user_id, Carpooling_id);
	}
	@Override
	public int InitiateCarpooling(
			String Carpooling_origin,
			String Carpooling_destination, String Carpooling_Date,
			int Carpooling_distance, String Carpooling_way,
			int Carpooling_count, double Carpooling_longitude,
			double Carpooling_latitude,
			Long Main_user_id) {
		
		return userDao.InitiateCarpooling(Carpooling_origin, Carpooling_destination, Carpooling_Date, Carpooling_distance, Carpooling_way, Carpooling_count, Carpooling_longitude, Carpooling_latitude,Main_user_id);
	}
	@Override
	public int updateCarpooling_status(Long Carpooling_id) {
		
		return userDao.updateCarpooling_status(Carpooling_id);
	}
	@Override
	public Carpooling_user JudgeCarpoolingByCarpooling_User_id(Long Carpooling_id,
			Long user_id) {
	
		return userDao.JudgeCarpoolingByCarpooling_User_id(Carpooling_id, user_id);
	}
	@Override
	public List<Carpooling> SelectCarpoolingByUser_id(Long user_id) {
	
		return userDao.SelectCarpoolingByUser_id(user_id);
	}
	@Override
	public List<Carpooling_user> SelectCarpooling_UserByUser_id(Long user_id) {
	
		return userDao.SelectCarpooling_UserByUser_id(user_id);
	}
	@Override
	public int InsertS_user(Long user_id, Long user_tel, String user_password,
			double user_longitude, double user_latitude, String user_name,
			String user_content) {
		
		return userDao.InsertS_user(user_id, user_tel, user_password, user_longitude, user_latitude, user_name, user_content);
	}
	@Override
	public int selectCarCountByCarpooling_id(Long Carpooling_id) {
		
		return userDao.selectCarCountByCarpooling_id(Carpooling_id);
	}
	@Override
	public int InsertS_userInfo(Long user_id) {
		
		return userDao.InsertS_userInfo(user_id);
	}
	@Override
	public User SearchSameUserName(Long user_id) {
		
		return userDao.SearchSameUserName(user_id);
	}
	
	//判断用户是否发起过拼车,或者是在某个拼车队伍
	@Override
	public JSONObject JudgeCarpoolingMan(List<Carpooling> carpoolings,Long user_id) {
		
		JSONObject jsonObject1 = new JSONObject();

		JSONArray jsonArray = new JSONArray();

		JSONArray jsonArray2 = new JSONArray();
		
		// 如果为空则证明登录用户没有发起过正在拼车的队伍
		if (carpoolings.toString().equals("[]")) {
			System.out.println("[]");
			List<Carpooling_user> carpooling_user = userDao
					.SelectCarpooling_UserByUser_id(user_id);
			// 该用户既没有发起拼车,也没加入拼车队伍
			if (carpooling_user.toString().equals("[]")) {

				jsonObject1.put("s_carpooling", jsonArray);

				jsonObject1.put("carpooling_user", jsonArray2);
				return CommonUtil.constructResponse(0, "该用户既没有发起拼车,也没加入拼车队伍",
						jsonObject1);
			}
			// 该用户没有发起拼车,但加入了拼车队伍
			else {
				for (int i = 0; i < carpooling_user.size(); i++) {
					JSONObject jsonObject2 = new JSONObject();

					Long Carpooling_id = carpooling_user.get(i)
							.getCarpooling_id();

					jsonObject2.put("Carpooling_id", Carpooling_id.toString());

					System.out.println("Carpooling_id:" + Carpooling_id);
					jsonArray.add(jsonObject2);
				}

				jsonObject1.put("s_carpooling", jsonArray2);

				jsonObject1.put("carpooling_user", jsonArray);

				return CommonUtil.constructResponse(1, "该用户没有发起拼车,但加入了拼车队伍",
						jsonObject1);
			}

		}

		else {

			List<Carpooling_user> carpooling_user = userDao
					.SelectCarpooling_UserByUser_id(user_id);
			// 该用户发起了拼车,也加入了拼车队伍
			if (!carpooling_user.toString().equals("[]")) {

				for (int i = 0; i < carpooling_user.size(); i++) {
					JSONObject jsonObject4 = new JSONObject();
					Long Carpooling_id = carpooling_user.get(i)
							.getCarpooling_id();

					jsonObject4.put("Carpooling_id", Carpooling_id.toString());

					System.out.println("carpooling_user_Carpooling_id:"
							+ Carpooling_id);
					jsonArray2.add(jsonObject4);
				}

				for (int i = 0; i < carpoolings.size(); i++) {

					JSONObject jsonObject3 = new JSONObject();

					Long Mian_Carpooling_id = carpoolings.get(i)
							.getCarpooling_id();

					jsonObject3.put("Mian_Carpooling_id",
							Mian_Carpooling_id.toString());

					System.out.println("carpoolings_Carpooling_id:"
							+ Mian_Carpooling_id);

					jsonArray.add(jsonObject3);
				}

				jsonObject1.put("s_carpooling", jsonArray);

				jsonObject1.put("carpooling_user", jsonArray2);

				return CommonUtil.constructResponse(1, "该用户发起了拼车,也加入了拼车队伍",
						jsonObject1);

			}

			// 该用户发起了拼车,但没加入拼车队伍
			else {

				for (int i = 0; i < carpoolings.size(); i++) {

					JSONObject jsonObject4 = new JSONObject();

					Long Carpooling_id = carpoolings.get(i).getCarpooling_id();

					jsonObject4.put("Carpooling_id", Carpooling_id.toString());

					System.out.println("Carpooling_id:" + Carpooling_id);

					jsonArray.add(jsonObject4);
				}

				jsonObject1.put("s_carpooling", jsonArray);

				jsonObject1.put("carpooling_user", jsonArray2);

				return CommonUtil.constructResponse(1, "该用户发起了拼车,但没加入拼车队伍",
						jsonObject1);
			}
		}
	}



	



}
