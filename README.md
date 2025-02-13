## Refleksi Kode

- **Clean Code:**
    - Menggunakan arsitektur MVC untuk memisahkan tanggung jawab antara controller, service, dan repository.
    - Penamaan kelas, metode, dan variabel yang jelas serta deskriptif.
    - Penerapan prinsip DRY dengan menggunakan metode seperti `findById` secara konsisten.

- **Secure Coding:**
    - Validasi input untuk mencegah serangan injeksi.
    - Konfirmasi aksi destruktif (misalnya, sebelum menghapus produk).
    - Penanganan error dengan mengarahkan pengguna ke halaman yang aman jika data tidak ditemukan.

- **Area untuk Peningkatan:**
    - Menambahkan custom exceptions untuk penanganan error yang lebih spesifik.
    - Mengintegrasikan validasi sisi server (Bean Validation) untuk menjaga integritas data.
    - Mengimplementasikan logging (misalnya, SLF4J & Logback) dan Spring Security untuk meningkatkan keamanan aplikasi.
