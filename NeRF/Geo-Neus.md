# Geo-Neus
> Geometry-Consistent Neural Implicit Surfaces Learning for Multi-view Reconstruction（用于多视图重建的几何一致的神经隐式曲面学习）
> NeurlIPS 2022

## 贡献

- 分析体渲染积分和 SDF 建模之间存在的差距

- 显式几何约束：直接定位 SDF 网络的零水平集，利用多视图几何约束明确监督 SDF 网络的训练

  - 稀疏的 3D 点提供 SDF 损失 -> 对纹理丰富的区域进行约束
  - 多视图约束的几何一致性监督 -> 对大面积的光滑区域进行约束

## 相关研究

### 传统多视图三维重建

structure from motion（SFM）：提取和匹配相邻视图的特征，并估计相机参数和稀疏的三维点

multi-view stereo（MVS）：估计每个视图的深度图，将所有深度图融合成密集点云，再使用表面重建方法从点云重建表面

### 曲面的隐式表示

occupancy function

SDF

### 神经隐式曲面重建

NeRF：使用 MLP 代表神经辐射场

IDR：表面表示为 SDF 的零水平集，使用神经网络重构曲面

MVSDF、VolSDF、UNISURF

## 问题

现有方法缺乏明确的多视图几何约束，无法生成几何一致的表面

现有的神经隐式曲面学习主要利用体渲染的损失来隐式优化 SDF 网络

## 方法

[![image.png](https://i.postimg.cc/j2J2wn1f/image.png)](https://postimg.cc/hQccNvc4)

### 1. 分析体渲染积分和SDF建模之间存在的差距

公式推导：渲染的颜色存在误差

### 2. 对SDF的显式监督

每张图像 I 的特征点 X -> 每个视图的可见点 P

SFM 得到的离散点在物体表面，从对应视图渲染图像时使用 P 来监督 SDF 网络

view-aware SDF loss（视图感知的 SDF 损失）：

[![image.png](https://i.postimg.cc/mrH8ZmLN/image.png)](https://postimg.cc/p9PDqYnp)

### 3. 多视图约束的几何一致性监督

#### 隐式表面捕获

沿着视图射线离散采样，并使用黎曼和来获得渲染的颜色。在采样点的基础上，采用线性插值方法得到曲面点  

获取采样点 i：i 处 SDF 值和 i+1 处采样点值异号，即它们之间存在表面，采样点即为交点  

为了感知遮挡，在样本点集合中选择最近的点，保证隐式曲面的样本点在对应的视图中都是可见的，并且使监督与显色过程一致

#### 多视图光度一致性约束

使用多视图立体(MVS)中的光度一致性约束来监督我们提取的隐式表面  

> 参考论文：
> - Accurate, dense, and robust multiview stereopsis. TPAMI, 2010.
> - Massively parallel multiview stereopsis by surface normal diffusion. ICCV, 2015.

> 补充概念：  
> - 光度一致性：在不同视角下观察到的同一点的颜色应该是一致的  

对于表面上的一小块区域 s, s 在图像上的投影是一个小的像素块 q。除遮挡情况外，s 对应的块在不同视图之间应该是几何一致的   

与传统 MVS 方法中的补片变形类似，使用中心点及其法线表示 s  

photometric consistency loss（光度一致性损失）：

[![image.png](https://i.postimg.cc/2SfzD6Cy/image.png)](https://postimg.cc/YhbcNtGB)

### 4. 损失函数

[![image.png](https://i.postimg.cc/J4Q1R80Q/image.png)](https://postimg.cc/3ddMZPgy)
