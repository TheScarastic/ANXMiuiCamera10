.class Lcom/android/camera/fragment/top/FragmentTopAlert$4;
.super Ljava/lang/Object;
.source "FragmentTopAlert.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/fragment/top/FragmentTopAlert;->alertSwitchHint(III)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/fragment/top/FragmentTopAlert;


# direct methods
.method constructor <init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V
    .locals 0

    .line 449
    iput-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert$4;->this$0:Lcom/android/camera/fragment/top/FragmentTopAlert;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    .line 452
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert$4;->this$0:Lcom/android/camera/fragment/top/FragmentTopAlert;

    invoke-virtual {v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->isAdded()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 453
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert$4;->this$0:Lcom/android/camera/fragment/top/FragmentTopAlert;

    invoke-static {v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->access$400(Lcom/android/camera/fragment/top/FragmentTopAlert;)Landroid/widget/TextView;

    move-result-object v0

    const v1, 0x8000

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->sendAccessibilityEvent(I)V

    .line 455
    :cond_0
    return-void
.end method
