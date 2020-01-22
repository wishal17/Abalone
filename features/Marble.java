package features;
 public enum Marble {
	EE, BB, WW, YY, RR;

 public static Marble returnTeammate(Marble m) {
	 switch(m) {
	 case WW:
		 return BB;
	 case BB:
		 return WW;
	 case YY:
		 return RR;
	 case RR:
		 return YY;
	 default:
		 return null;
	 }
 }
 public boolean isTeammate(Marble m) {
	 return this == m || this == returnTeammate(m);
 }
 }
