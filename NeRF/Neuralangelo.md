# Neuralangelo
> High-Fidelity Neural Surface Reconstruction
> CVPR 2023

## 贡献
- 将多分辨率哈希编码的表示能力引入神经 SDF 表示
- 计算高阶导数的数值梯度用于平滑操作
- 对不同细节级别的哈希网格进行渐进式的，从粗到细的优化

## 问题
神经表面重建方法：重建的保真度并不能很好地随 MLP 的容量进行扩展，即内存占用增加，重建效果不一定更好

## 方法
### 数值梯度

### 渐进式细节级别（Progressive Levels of Details）

