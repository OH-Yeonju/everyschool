import 'package:everyschool/page/mypage/change_password.dart';
import 'package:everyschool/page/mypage/my_like_post.dart';
import 'package:everyschool/page/mypage/userinfo_correction.dart';
import 'package:flutter/material.dart';

class MypagePersonalMenu extends StatefulWidget {
  const MypagePersonalMenu({super.key});

  @override
  State<MypagePersonalMenu> createState() => _MypagePersonalMenuState();
}

class _MypagePersonalMenuState extends State<MypagePersonalMenu> {
  int userNum = 2;
  var perMenulist = [
    ['학부모 등록하기', '좋아요한 글', '개인정보 수정', '비밀번호 변경'],
    ['자녀 등록하기', '좋아요한 글', '개인정보 수정', '비밀번호 변경'],
    ['공지사항 관리', '비밀번호 변경']
  ];

  var perPagelist = [
    [MyLikePost(), MyLikePost(), UserInfoCorrection(), ChangePassword()],
    [MyLikePost(), MyLikePost(), UserInfoCorrection(), ChangePassword()],
    [UserInfoCorrection(), ChangePassword()]
  ];

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.fromLTRB(30, 35, 30, 20),
      width: MediaQuery.of(context).size.width,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: List.generate(
          perMenulist[userNum - 1].length,
          (index) {
            return GestureDetector(
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => perPagelist[userNum - 1][index]),
                );
              },
              child: Container(
                padding: EdgeInsets.fromLTRB(15, 20, 15, 20),
                margin: EdgeInsets.fromLTRB(0, 0, 0, 10),
                width: MediaQuery.of(context).size.width,
                decoration: BoxDecoration(
                    border: Border.all(width: 1, color: Color(0xff9A9A9A)),
                    borderRadius: BorderRadius.circular(8)),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Text(perMenulist[userNum - 1][index],
                        style: TextStyle(fontWeight: FontWeight.w600)),
                    Icon(
                      Icons.arrow_forward_ios,
                      size: 14,
                    )
                  ],
                ),
              ),
            );
          },
        ),
      ),
    );
  }
}