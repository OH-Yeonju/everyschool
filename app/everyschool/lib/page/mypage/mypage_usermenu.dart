import 'package:everyschool/page/community/post_list.dart';
import 'package:everyschool/page/community/postlist_page.dart';
import 'package:everyschool/page/consulting/consulting_list_parent.dart';
import 'package:everyschool/page/consulting/consulting_reservation_page.dart';
import 'package:everyschool/page/mypage/my_comment_post.dart';
import 'package:everyschool/page/mypage/my_like_post.dart';
import 'package:everyschool/page/mypage/my_write_list.dart';
import 'package:everyschool/page/report_consulting/consulting_list_teacher.dart';
import 'package:everyschool/page/report_consulting/teacher_report_consulting_page.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class MypageUsermenu extends StatefulWidget {
  const MypageUsermenu({super.key});

  @override
  State<MypageUsermenu> createState() => _MypageUsermenuState();
}

class _MypageUsermenuState extends State<MypageUsermenu> {
  final storage = FlutterSecureStorage();

  var menulist = [
    ['작성한 글 보기', '작성한 댓글 보기'],
    ['상담 내역', '상담 신청'],
    ['상담 목록', '신고 목록'],
  ];

  var imagelist = [
    ['search', 'consulting'],
    ['consulting', 'menu'],
    ['consulting', 'menu']
  ];

  var perPagelist = [
    [MyWriteList(), MyCommentPost()],
    [ConsultingListParent(), ConsultingReservation()],
    [ReportConsultingPage(index: 0), ReportConsultingPage(index: 1)],
  ];

  getuserType() async {
    var userType = await storage.read(key: 'usertype');
    var intUserType = userType!.substring(userType.length - 1);

    return int.parse(intUserType);
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: getuserType(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.hasData) {
            return Container(
              margin: EdgeInsets.fromLTRB(0, 35, 0, 10),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: List.generate(
                  menulist[snapshot.data - 1].length,
                  (index) {
                    return GestureDetector(
                      onTap: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) =>
                                  perPagelist[snapshot.data - 1][index]),
                        );
                      },
                      child: Column(
                        children: [
                          Image.asset(
                            'assets/images/mypage/${imagelist[snapshot.data - 1][index]}.png',
                            width: 40,
                            height: 40,
                          ),
                          SizedBox(
                            height: 5,
                          ),
                          Text(menulist[snapshot.data - 1][index],
                              style: TextStyle(fontWeight: FontWeight.w600)),
                        ],
                      ),
                    );
                  },
                ),
              ),
            );
          } else if (snapshot.hasError) {
            return Container(
              height: 800,
            );
          } else {
            return Container(
              height: 800,
            );
          }
        });
  }
}
