package com.bdtc.technews.populate;

public enum NewsBody {
    BODY1("Lorem ipsum dolor sit amet"),
    BODY2("Integer vitae nisi vitae velit euismod feugiat. Morbi rhoncus, ex id laoreet mollis, odio nunc posuere eros, ut viverra augue justo non libero. Nulla euismod ligula id purus fermentum fermentum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Quisque vehicula sapien eu enim aliquet, sed consectetur turpis ultrices."),
    BODY3("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed eget tortor eu felis ultricies sollicitudin. Integer vitae nisi vitae velit euismod feugiat. Morbi rhoncus, ex id laoreet mollis, odio nunc posuere eros, ut viverra augue justo non libero. Nulla euismod ligula id purus fermentum fermentum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Quisque vehicula sapien eu enim aliquet, sed consectetur turpis ultrices.\\n\" +\n" +
            "                \"\\n\" +\n" +
            "                \"Suspendisse potenti. Integer nec neque vel velit faucibus pharetra. Mauris in erat ut sapien fermentum sodales. Vestibulum tincidunt erat quis nisi congue ullamcorper. Proin nec convallis lorem. Donec id leo et turpis vestibulum consequat. In hac habitasse platea dictumst. Nulla rutrum risus non ligula dictum, nec rutrum tortor fermentum.\\n\" +\n" +
            "                \"\\n\" +\n" +
            "                \"Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Fusce auctor velit non finibus lobortis. Cras vel metus a est bibendum interdum sed nec est. Nulla facilisi. Aliquam erat volutpat. Morbi consectetur sapien non neque aliquet, eget commodo metus pharetra. Aliquam tincidunt libero ac urna ultricies, eget aliquam arcu tincidunt.\\n\" +\n" +
            "                \"\\n\" +\n" +
            "                \"Pellentesque quis ante quis sapien facilisis dapibus. In sagittis sapien id eros dictum, eu tempor risus lobortis. Fusce scelerisque erat non justo ultricies, quis mattis mauris malesuada. Integer ut consequat libero. Ut sed nunc sapien. Fusce vehicula, velit at suscipit malesuada, justo velit scelerisque turpis, nec fermentum quam orci sit amet ipsum.\\n\" +\n" +
            "                \"\\n\" +\n" +
            "                \"Vivamus sit amet justo a felis tempor interdum. Phasellus eleifend pretium felis, nec placerat leo fermentum in. Quisque quis libero sit amet libero sollicitudin mattis. Suspendisse potenti. Nulla id libero eu lacus aliquam ultrices sed eget ex. Sed congue ligula sit amet libero consectetur, eget blandit metus tincidunt. Sed convallis ultricies magna. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed eget tortor eu felis ultricies sollicitudin. Integer vitae nisi vitae velit euismod feugiat. Morbi rhoncus, ex id laoreet mollis, odio nunc posuere eros, ut viverra augue justo non libero. Nulla euismod ligula id purus fermentum fermentum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Quisque vehicula sapien eu enim aliquet, sed consectetur turpis ultrices.\\n\" +\n" +
            "                \"\\n\" +\n" +
            "                \"Suspendisse potenti. Integer nec neque vel velit faucibus pharetra. Mauris in erat ut sapien fermentum sodales. Vestibulum tincidunt erat quis nisi congue ullamcorper. Proin nec convallis lorem. Donec id leo et turpis vestibulum consequat. In hac habitasse platea dictumst. Nulla rutrum risus non ligula dictum, nec rutrum tortor fermentum.\\n\" +\n" +
            "                \"\\n\" +\n" +
            "                \"Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Fusce auctor velit non finibus lobortis. Cras vel metus a est bibendum interdum sed nec est. Nulla facilisi. Aliquam erat volutpat. Morbi consectetur sapien non neque aliquet, eget commodo metus pharetra. Aliquam tincidunt libero ac urna ultricies, eget aliquam arcu tincidunt.\\n\" +\n" +
            "                \"\\n\" +\n" +
            "                \"Pellentesque quis ante quis sapien facilisis dapibus. In sagittis sapien id eros dictum, eu tempor risus lobortis. Fusce scelerisque erat non justo ultricies, quis mattis mauris malesuada. Integer ut consequat libero. Ut sed nunc sapien. Fusce vehicula, velit at suscipit malesuada, justo velit scelerisque turpis, nec fermentum quam orci sit amet ipsum.\\n\" +\n" +
            "                \"\\n\" +\n" +
            "                \"Vivamus sit amet justo a felis tempor interdum. Phasellus eleifend pretium felis, nec placerat leo fermentum in. Quisque quis libero sit amet libero sollicitudin mattis. Suspendisse potenti. Nulla id libero eu lacus aliquam ultrices sed eget ex. Sed congue ligula sit amet libero consectetur, eget blandit metus tincidunt. Sed convallis ultricies magna.");

    private String txt;
    private NewsBody(String txt) {
        this.txt = txt;
    }

    public String getTxt() {
        return txt;
    }
}
