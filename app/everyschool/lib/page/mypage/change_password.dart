import 'package:flutter/material.dart';

class ChangePassword extends StatefulWidget {
  const ChangePassword({super.key});

  @override
  State<ChangePassword> createState() => _ChangePasswordState();
}

class _ChangePasswordState extends State<ChangePassword> {
  TextEditingController curPassword = TextEditingController();
  TextEditingController newPassword = TextEditingController();
  TextEditingController confirmPassword = TextEditingController();

  bool newPwdValidation = false;
  bool sameNewPwdValidation = false;

  String? passwordError;
  String passwordMessage = '특수문자, 숫자, 영어가 필수로 1개씩 있어야 합니다.';
  String? samepasswordError;
  String samepasswordMessage = '비밀번호와 다릅니다.';

  Color properColor = Colors.red;

  TextStyle myTextStyle = TextStyle(fontSize: 18, fontWeight: FontWeight.bold);
  TextStyle unabledTextStyle =
      TextStyle(fontSize: 18, color: Color(0xff868E96));
  TextStyle unabledTitleTextStyle = TextStyle(
      fontSize: 18, color: Color(0xff868E96), fontWeight: FontWeight.bold);

  void passwordValidation(value) {
    if (!RegExp(
            r"^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$")
        .hasMatch(value)) {
      setState(() {
        passwordError = passwordMessage;
        newPwdValidation = false;
      });
    } else {
      setState(() {
        passwordError = null; // 에러 없음
        newPwdValidation = true;
      });
    }
    properColor = newPwdValidation ? Color(0XFF15075F) : Colors.red;
  }

  void samePasswordValidation(value) {
    if (newPassword.text != confirmPassword.text) {
      setState(() {
        samepasswordError = samepasswordMessage;
        sameNewPwdValidation = false;
      });
    } else {
      setState(() {
        samepasswordError = null; // 에러 없음
        sameNewPwdValidation = true;
      });
    }
    properColor = sameNewPwdValidation ? Color(0XFF15075F) : Colors.red;
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        elevation: 2,
        backgroundColor: Colors.grey[50],
        leading: BackButton(color: Colors.black),
        centerTitle: true,
        title: Text(
          '비밀번호 변경',
          style: TextStyle(
              color: Colors.black, fontSize: 18, fontWeight: FontWeight.w700),
        ),
      ),
      body: Container(
        margin: EdgeInsets.fromLTRB(30, 30, 30, 10),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('새로운 비밀번호', style: myTextStyle),
            Padding(
              padding: EdgeInsets.fromLTRB(0, 5, 0, 5),
              child: TextField(
                onChanged: passwordValidation,
                maxLength: 20,

                controller: newPassword,
                keyboardType: TextInputType.visiblePassword,
                obscureText: true, // 비밀번호 안보이도록 하는 것
                decoration: InputDecoration(
                    errorText: passwordError,
                    counterText: '',
                    contentPadding:
                        EdgeInsets.symmetric(vertical: 16.0, horizontal: 10.0),
                    focusedBorder: OutlineInputBorder(
                        borderSide:
                            BorderSide(width: 1.5, color: Color(0XFF15075F))),
                    border: OutlineInputBorder(borderSide: BorderSide()),
                    focusColor: Color(0XFF15075F)),
              ),
            ),
            SizedBox(
              height: 10,
            ),
            Text('비밀번호 확인', style: myTextStyle),
            Padding(
              padding: EdgeInsets.fromLTRB(0, 5, 0, 10),
              child: TextField(
                maxLength: 20,
                onChanged: samePasswordValidation,
                controller: confirmPassword,
                obscureText: true, // 비밀번호 안보이도록 하는 것
                decoration: InputDecoration(
                    errorText: samepasswordError,
                    counterText: '',
                    contentPadding:
                        EdgeInsets.symmetric(vertical: 16.0, horizontal: 10.0),
                    focusedBorder: OutlineInputBorder(
                        borderSide:
                            BorderSide(width: 1.5, color: Color(0XFF15075F))),
                    border: OutlineInputBorder(borderSide: BorderSide()),
                    focusColor: Color(0XFF15075F)),
              ),
            ),
            SizedBox(
              height: 18,
            ),
            SizedBox(
              height: 60,
              child: Padding(
                padding: EdgeInsets.fromLTRB(0, 0, 0, 10),
                child: ButtonTheme(
                    child: TextButton(
                        onPressed: () {},
                        style: ButtonStyle(
                            backgroundColor:
                                MaterialStatePropertyAll(Color(0XFF15075F))),
                        child: SizedBox(
                          height: 40,
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: const [
                              Text(
                                '로그인',
                                style: TextStyle(
                                  color: Colors.white,
                                  fontWeight: FontWeight.w700,
                                ),
                              ),
                            ],
                          ),
                        ))),
              ),
            ),
          ],
        ),
      ),
    );
  }
}