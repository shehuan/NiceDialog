# NiceDialog

### Introduction:
* Based on the extension of DialogFragment, the use of this dialog is more convenient
* Principle: <http://www.jianshu.com/p/0529433d4522>
### Sample preview:

|![](image/share.gif)|![](image/set.gif)|![](image/commit.gif)|
|---|---|---|
|![](image/red_packet.gif)|![](image/loading.gif)|![](image/confirm.gif)|

### Basic Usage：
**Step 1. Add a JitPack repository**

Add the following to the build.gradle file in the current project root directory:
``` gradle
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
**Step 2. Add project dependencies**
``` gradle
dependencies {
        implementation 'com.github.SheHuan:NiceDialog:1.2.0'
}
```
**Step 3. Configure and display the dialog**
```java
NiceDialog.init()
          .setLayoutId(R.layout.dialog)     // Set the dialog layout file
          .setTheme(R.style.MyDialog) // Set the dialog theme, the default theme is inherited from Theme.AppCompat.Light.Dialog
          .setConvertListener(new ViewConvertListener() {     // Callback for related View operations
              @Override
              public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {

              }
          })
          .setDimAmount(0.3f)     // Adjust the background transparency [0-1], default 0.5f
          .setGravity()     // Optional, set the dialog's position, default to center，Can be modified by the constants of the system Gravity class，For example Gravity.BOTTOM (bottom)，Gravity.Right（right），Gravity.BOTTOM|Gravity.Right（bottom right）
          .setMargin()     // Distance from left and right to the edge of the screen (unit: dp), default 0dp
          .setWidth()     // Dialog's width (unit: dp), default is the screen width, -1 stands for WRAP_CONTENT
          .setHeight()     // Dialog's height (unit: dp), default is WRAP_CONTENT
          .setOutCancel(false)     // You can cancel by clicking outside the dialog, the default is true
          .setAnimStyle(R.style.EnterExitAnimation)     // Set the dialog to enter、exit custom animation; according to the set Gravity，By default, the left, top, right, and bottom positions are provided to enter the exit animation.
          .show(getSupportFragmentManager());     // Display the dialog
```
**Notes:** `setMargin()` and `setWidth()` to select one

### More Usage：
##### 1. Create a class that inherits from BaseNiceDialog. If you need to pass the parameters, you can follow the following method, which is also the common method of Fragment.
```java
public class ConfirmDialog extends BaseNiceDialog {
        private String type;

        public static ConfirmDialog newInstance(String type) {
            Bundle bundle = new Bundle();
            bundle.putString("type", type);
            ConfirmDialog dialog = new ConfirmDialog();
            dialog.setArguments(bundle);
            return dialog;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle bundle = getArguments();
            type = bundle.getString("type");
        }

        @Override
        public int intLayoutId() {
            return R.layout.dialog;
        }

        @Override
        public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {

        }
    }
```
##### 2. Show dialog
```java
ConfirmDialog.newInstance("1")
             .setMargin(60)
             .setOutCancel(false)
             .show(getSupportFragmentManager());
```
